package com.madeofair.webapp.pages

import com.madeofair.models.domain.*
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
import com.madeofair.repositories.PitchforkRepository
import io.ktor.application.call
import io.ktor.locations.Location
import io.ktor.locations.get
import io.ktor.routing.Route
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

const val LOAD_CSV = "/load_csv"

@Location(LOAD_CSV)
class LoadCsv

fun Route.loadCsv(musicRepository: MusicRepository, pitchforkRepository: PitchforkRepository) {
    get<LoadCsv> {
        val type: String? = call.request.queryParameters["type"]
        if (type != null) {
            loadCsv(type, musicRepository, pitchforkRepository)
        }

        if (type == "music") {
            call.redirect(Stats())
        } else if (type == "pitchfork") {
            call.redirect(StatsPitchfork())
        }
    }
}

suspend fun loadCsv(type: String, musicRepository: MusicRepository, pitchforkRepository: PitchforkRepository) {
    var fileReader: BufferedReader? = null
    var csvParser: CSVParser? = null

    val resourcesOtherPath = "./resources/other/"
    val csvExtension = ".csv"

    try {
        fileReader = BufferedReader(FileReader(resourcesOtherPath + type + csvExtension))
        csvParser = CSVParser(
            fileReader,
            CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
        )

        if (type == "music") {
            musicRepository.clearAll()

            val csvRecords = csvParser.records
            for (csvRecord in csvRecords) {
                val year = csvRecord.get("YEAR")
                val month = csvRecord.get("MONTH")
                val band = csvRecord.get("BAND")
                val album = csvRecord.get("ALBUM")
                val genre = csvRecord.get("GENRE")
                val rating = csvRecord.get("RATING")
                val bestSong = csvRecord.get("BEST SONG")

                val albumToAdd = Music(
                    "",
                    yearStringToYearEnum(year),
                    monthStringToMonthEnum(month),
                    band,
                    album,
                    genreStringToGenreEnum(genre),
                    rating,
                    bestSong
                )

                musicRepository.add(albumToAdd)
            }
        } else if (type == "pitchfork") {
            pitchforkRepository.clearAll()

            val csvRecords = csvParser.records
            for (csvRecord in csvRecords) {
                val year = csvRecord.get("YEAR")
                val month = csvRecord.get("MONTH")
                val day = csvRecord.get("DAY")
                val band = csvRecord.get("BAND")
                val album = csvRecord.get("ALBUM")
                val rating = csvRecord.get("RATING")

                val pitchforkToAdd = Pitchfork(
                    "",
                    yearStringToYearEnum(year),
                    monthStringToMonthEnum(month),
                    day.toInt(),
                    band,
                    album,
                    rating
                )

                pitchforkRepository.add(pitchforkToAdd)

            }
        }
    } catch (e: Exception) {
        println("Reading CSV Error!")
        e.printStackTrace()
    } finally {
        try {
            fileReader!!.close()
            csvParser!!.close()
        } catch (e: IOException) {
            println("Closing fileReader/csvParser Error!")
            e.printStackTrace()
        }
    }
}
