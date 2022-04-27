package ua.zloydi.recipeapp.ui.search.filter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import ua.zloydi.recipeapp.models.filterTypes.*

data class FilterUI(
    val name: String,
    val isSelected: Boolean,
    val onClick: () -> Unit
)

data class FilterState(
    val categories: MutableList<FilterUI> = mutableListOf(),
    val meals: MutableList<FilterUI> = mutableListOf(),
    val cuisines: MutableList<FilterUI> = mutableListOf(),
)

data class FilterSelectable<out T : FilterType>(
    val filter: T,
    val isSelected: Boolean
)

sealed class Action{
    data class Select(val type: FilterType, val position: Int) : Action()
    object UnselectAll : Action()
}

class FilterViewModel(filter: Filter) : ViewModel() {
    private val _actions: Channel<Action> = Channel()
    private val _state: MutableStateFlow<FilterState>

    private val categories = Dish.values.toFilterSelectable(filter.categories)
    private val meals = Meal.values.toFilterSelectable(filter.meals)
    private val cuisines = Cuisine.values.toFilterSelectable(filter.cuisines)

    private fun <T : FilterType> List<T>.toFilterSelectable(selected: List<T>) = mapTo(mutableListOf()){
        FilterSelectable(it, selected.indexOf(it) != -1)
    }

    init {
        _state = MutableStateFlow(
            FilterState(
                categories.toFilterUI(),
                meals.toFilterUI(),
                cuisines.toFilterUI()
            )
        )
    }

    private fun<T : FilterType> MutableList<out FilterSelectable<T>>.toFilterUI() =
        mapIndexedTo(mutableListOf()){ index, it ->
            FilterUI(it.filter.label, it.isSelected) { select(it, index) }
        }

    private fun select(item: FilterSelectable<FilterType>, position: Int)
        = viewModelScope.launch(Dispatchers.IO){
            val uiItems: MutableList<FilterUI>
            val items: MutableList<FilterSelectable<FilterType>>
            when(item.filter){
                is Dish -> {
                    uiItems = _state.value.categories
                    items = categories as MutableList<FilterSelectable<FilterType>>
                }
                is Meal -> {
                    uiItems = _state.value.meals
                    items = meals as MutableList<FilterSelectable<FilterType>>
                }
                is Cuisine -> {
                    uiItems = _state.value.cuisines
                    items = cuisines as MutableList<FilterSelectable<FilterType>>
                }
            }

            items[position] = items[position].copy(isSelected = !items[position].isSelected)
            uiItems[position] = uiItems[position].copy(isSelected = items[position].isSelected)
            _actions.send(Action.Select(item.filter, position))
        }

    val state = _state.asStateFlow()
    val actions = _actions.consumeAsFlow()

    fun getFilter() = Filter(
        categories = categories.selected(),
        meals = meals.selected(),
        cuisines = cuisines.selected(),
    )

    private fun<T : FilterType> List<FilterSelectable<T>>.selected() =
        filter { it.isSelected }
        .map { it.filter }

    fun reset() = viewModelScope.launch(Dispatchers.Default){
        categories.unselect()
        meals.unselect()
        cuisines.unselect()
        with(_state.value) {
            categories.unselect()
            meals.unselect()
            cuisines.unselect()
        }
        _actions.send(Action.UnselectAll)
    }

    private fun<T : FilterType> MutableList<FilterSelectable<T>>.unselect(){
        for (index in indices)
            this[index] = this[index].copy(isSelected = false)
    }

    @JvmName("unselectFilterUI")
    private fun MutableList<FilterUI>.unselect(){
        for (index in indices)
            this[index] = this[index].copy(isSelected = false)
    }

    class Factory(
        private val filter: Filter
    ) : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(FilterViewModel::class.java))
                return FilterViewModel(filter) as T
            else
                throw TypeCastException()
        }
    }
}