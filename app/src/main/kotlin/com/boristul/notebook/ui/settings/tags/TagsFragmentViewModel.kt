package com.boristul.notebook.ui.settings.tags

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.boristul.repository.TagsRepository
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.closestDI
import org.kodein.di.instance

class TagsFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by closestDI()
    private val tagsRepository by instance<TagsRepository>()

    val tags = tagsRepository.getAll()
    val nameLiveData = MutableLiveData("")

    fun addTag() {
        viewModelScope.launch { tagsRepository.insert(checkNotNull(nameLiveData.value)) }
    }

    fun delete(id: Long) {
        viewModelScope.launch { tagsRepository.delete(id) }
    }
}
