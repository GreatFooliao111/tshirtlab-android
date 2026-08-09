package com.example.data.local

import android.content.Context
import com.example.data.DesignItem
import com.example.data.ItemType
import com.example.data.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class DesignRepository(private val dao: DesignItemDao) {

    val allItems: Flow<List<DesignItem>> = dao.getAllItems().map { list ->
        list.map { it.toDomainModel() }
    }

    val styles: Flow<List<DesignItem>> = dao.getItemsByType(ItemType.STYLE.name).map { list ->
        list.map { it.toDomainModel() }
    }

    val techniques: Flow<List<DesignItem>> = dao.getItemsByType(ItemType.TECHNIQUE.name).map { list ->
        list.map { it.toDomainModel() }
    }

    val presets: Flow<List<DesignItem>> = dao.getItemsByType(ItemType.PRESET.name).map { list ->
        list.map { it.toDomainModel() }
    }

    suspend fun getItemById(id: String): DesignItem? = withContext(Dispatchers.IO) {
        dao.getItemByIdSync(id)?.toDomainModel() ?: Repository.getItemById(id)
    }

    fun searchItems(query: String, filterType: ItemType? = null): Flow<List<DesignItem>> {
        return (if (filterType != null) {
            dao.getItemsByType(filterType.name)
        } else {
            dao.getAllItems()
        }).map { list ->
            val q = query.trim().lowercase()
            list.map { it.toDomainModel() }.filter { item ->
                if (q.isEmpty()) true
                else {
                    item.nameFa.lowercase().contains(q) ||
                    item.nameEn.lowercase().contains(q) ||
                    item.taglineFa.lowercase().contains(q) ||
                    item.searchKeywords.any { it.lowercase().contains(q) }
                }
            }
        }
    }

    suspend fun ensureSeeded() = withContext(Dispatchers.IO) {
        if (dao.getCount() == 0) {
            val entities = Repository.allItems.map { DesignItemEntity.fromDomainModel(it) }
            dao.insertAll(entities)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: DesignRepository? = null

        fun getInstance(context: Context): DesignRepository {
            return INSTANCE ?: synchronized(this) {
                val db = AppDatabase.getInstance(context)
                val repo = DesignRepository(db.designItemDao())
                INSTANCE = repo
                repo
            }
        }
    }
}
