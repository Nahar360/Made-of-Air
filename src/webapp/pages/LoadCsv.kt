package com.madeofair.webapp.pages

import com.madeofair.models.domain.Music
import com.madeofair.models.domain.genreStringToGenreEnum
import com.madeofair.models.domain.monthStringToMonthEnum
import com.madeofair.models.domain.yearStringToYearEnum
import com.madeofair.redirect
import com.madeofair.repositories.MusicRepository
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

fun Route.loadCsv(musicRepository: MusicRepository) {
    get<LoadCsv> {
        musicRepository.clearAll()

        var fileReader: BufferedReader? = null
        var csvParser: CSVParser? = null

        try {
            fileReader = BufferedReader(FileReader("./resources/other/music.csv"))
            csvParser = CSVParser(
                fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim()
            )

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

        call.redirect(Stats())
    }
}
