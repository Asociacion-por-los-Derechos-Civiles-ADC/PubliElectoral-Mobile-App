/*
 * Copyright 2018 Allan Wang
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pitchedapps.frost.enums

import androidx.annotation.StringRes
import com.pitchedapps.frost.R

enum class Location(@StringRes val textRes: Int, val item: String) {
    NONE(R.string.select_location, ""),
    PROVINCIA_BUENOS_AIRES(R.string.provincia_buenos_aires, "Provincia de Buenos Aires"),
    CAPITAL_FEDERAL(R.string.capital_federal, "Capital Federal");

    companion object {
        val values = values() // save one instance
        operator fun invoke(index: Int) = values[index]
    }
}
