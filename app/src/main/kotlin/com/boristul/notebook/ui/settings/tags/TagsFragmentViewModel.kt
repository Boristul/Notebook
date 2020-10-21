package com.boristul.notebook.ui.settings.tags

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.boristul.repository.TagsRepository
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.x.di
import org.kodein.di.instance

class TagsFragmentViewModel(application: Application) : AndroidViewModel(application), DIAware {
    override val di: DI by di()
    private val tagsRepository by instance<TagsRepository>()

    val tags = tagsRepository.getAllLiveData()

    val nameLiveData = MutableLiveData("")

    suspend fun addTag() = tagsRepository.insert(checkNotNull(nameLiveData.value))
    suspend fun delete(id: Long) = tagsRepository.delete(id)
}
