package com.boristul.notebook.ui.settings.tags

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boristul.repository.TagsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagsFragmentViewModel @Inject constructor(
    private val tagsRepository: TagsRepository
) : ViewModel() {

    val tags = tagsRepository.getAll()
    val nameLiveData = MutableLiveData("")

    fun addTag() {
        viewModelScope.launch { tagsRepository.insert(checkNotNull(nameLiveData.value)) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { tagsRepository.delete(id) }
    }
}
