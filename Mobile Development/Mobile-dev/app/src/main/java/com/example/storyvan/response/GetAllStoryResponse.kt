package com.example.storyvan.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class GetAllStoryResponse(

	@field:SerializedName("pengajuan_event")
	val listStory: List<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

@Parcelize
data class ListStoryItem(

	@field:SerializedName("nama_event")
	val nama_event: String,

	@field:SerializedName("jenis_event")
	val jenis_event: String,

	@field:SerializedName("cara_transaksi")
	val cara_transaksi: String,

	@field:SerializedName("tgl_mulai")
	val tanggal_mulai: String,

	@field:SerializedName("tgl_berakhir")
	val tanggal_berakhir : String,

	@field:SerializedName("deskripsi")
	val deskripsi: String,

	@field:SerializedName("foto")
	val foto: String,

	@field:SerializedName("id")
	val id: String

): Parcelable
