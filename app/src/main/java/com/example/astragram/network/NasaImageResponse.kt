package com.example.astragram.network

data class NasaImageResponse(
    val collection: CollectionData
)
data class CollectionData(
    val items: List<Item>,
    val version: String? = null,
    val href: String? = null
)

data class Item(
    val href: String,
    val data: List<ImageData>?,
    val links: List<Link>?
)

data class ImageData(
    val album: List<String>? = null,
    val center: String? = null,
    val title: String? = null,
    val keywords: List<String>? = null,
    val location: String? = null,
    val nasa_id: String? = null,
    val date_created: String? = null,
    val media_type: String? = null,
    val description: String? = null,
    val description_508: String? = null,
    val secondary_creator: String? = null,
    val photographer: String? = null
)

data class Link(
    val href: String,
    val rel: String,
    val render: String
)