package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data.DesignItem
import com.example.data.ItemType

@Entity(tableName = "design_items")
data class DesignItemEntity(
    @PrimaryKey val id: String,
    val nameFa: String,
    val nameEn: String,
    val type: String,
    val taglineFa: String,
    val descriptionFa: String,
    val whenToUseFa: String,
    val pairingsFa: String,
    val dtfTipFa: String,
    val promptFragment: String,
    val visualStyleKey: String,
    val presetStyleIds: List<String>,
    val presetTechniqueIds: List<String>,
    val searchKeywords: List<String>
) {
    fun toDomainModel(): DesignItem {
        return DesignItem(
            id = id,
            nameFa = nameFa,
            nameEn = nameEn,
            type = try { ItemType.valueOf(type) } catch (e: Exception) { ItemType.STYLE },
            taglineFa = taglineFa,
            descriptionFa = descriptionFa,
            whenToUseFa = whenToUseFa,
            pairingsFa = pairingsFa,
            dtfTipFa = dtfTipFa,
            promptFragment = promptFragment,
            visualStyleKey = visualStyleKey,
            presetStyleIds = presetStyleIds,
            presetTechniqueIds = presetTechniqueIds,
            searchKeywords = searchKeywords
        )
    }

    companion object {
        fun fromDomainModel(item: DesignItem): DesignItemEntity {
            return DesignItemEntity(
                id = item.id,
                nameFa = item.nameFa,
                nameEn = item.nameEn,
                type = item.type.name,
                taglineFa = item.taglineFa,
                descriptionFa = item.descriptionFa,
                whenToUseFa = item.whenToUseFa,
                pairingsFa = item.pairingsFa,
                dtfTipFa = item.dtfTipFa,
                promptFragment = item.promptFragment,
                visualStyleKey = item.visualStyleKey,
                presetStyleIds = item.presetStyleIds,
                presetTechniqueIds = item.presetTechniqueIds,
                searchKeywords = item.searchKeywords
            )
        }
    }
}
