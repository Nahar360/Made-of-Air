package com.madeofair.webapp.pages

import com.madeofair.models.domain.Music
import com.madeofair.models.domain.monthStringToMonthEnum
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
import java.time.Year

const val LOAD_CSV = "/load_csv"

@Location(LOAD_CSV)
class LoadCsv

fun Route.loadCsv(musicRepository: MusicRepository) {
    get<LoadCsv> {
        musicRepository.clearAll()

        var fileReader: BufferedReader? = null
        var csvParser: CSVParser? = null

//        var genres = ArrayList<String>()

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

                if (year != "" && month != "" && band != "" && album != "" && genre != "") {
                    // album without rating
                    val albumToAdd = if (rating == "") {
                        Music("", Year.parse(year), monthStringToMonthEnum(month), band, album, genre, 0, bestSong)
                    } else
                        Music("", Year.parse(year), monthStringToMonthEnum(month), band, album, genre, Integer.parseInt(rating) * 10, bestSong)

//                    if (!genres.contains(genre))
//                        genres.add((genre))

                    musicRepository.add(albumToAdd)
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

//        genres.sort()
//        for (genre in genres)
//            println(genre)

        call.redirect(Home())
    }
}
