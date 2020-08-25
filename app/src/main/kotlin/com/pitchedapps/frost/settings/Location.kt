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
package com.pitchedapps.frost.settings

import ca.allanwang.kau.kpref.activity.KPrefAdapterBuilder
import ca.allanwang.kau.kpref.activity.items.KPrefColorPicker
import ca.allanwang.kau.kpref.activity.items.KPrefSeekbar
import ca.allanwang.kau.ui.views.RippleCanvas
import ca.allanwang.kau.utils.materialDialog
import ca.allanwang.kau.utils.string
import com.afollestad.materialdialogs.list.listItemsSingleChoice
import com.pitchedapps.frost.R
import com.pitchedapps.frost.activities.SettingsActivity
import com.pitchedapps.frost.enums.Location
import com.pitchedapps.frost.enums.Country
import com.pitchedapps.frost.injectors.CssAssets
import com.pitchedapps.frost.utils.frostEvent
import com.pitchedapps.frost.utils.frostNavigationBar
import com.pitchedapps.frost.utils.frostSnackbar
import com.pitchedapps.frost.utils.launchTabCustomizerActivity
import com.pitchedapps.frost.views.KPrefTextSeekbar

fun SettingsActivity.getLocationPrefs(): KPrefAdapterBuilder.() -> Unit = {

    text(R.string.country, prefs::country, { prefs.country = it }) {
        onClick = {
            materialDialog {
                title(R.string.country)
                listItemsSingleChoice(
                    items = Country.values().map { string(it.textRes) },
                    initialSelection = item.pref
                ) { _, index, _ ->
                    if (item.pref != index) {
                        item.pref = index
                        prefs.location = 0
                        reload()
                        frostEvent("Country", "Count" to Country(index).name)
                    }
                }
            }
        }
        textGetter = {
            string(Country(it).textRes)
        }
    }

    text(R.string.location, prefs::location, { prefs.location = it }) {
        onClick = {
            materialDialog {
                title(R.string.location)
                listItemsSingleChoice(
                    items = Location.values().filter { it.country == prefs.country || it.country == 0 }.map { string(it.textRes) },
                    initialSelection = item.pref
                ) { _, index, _ ->
                    if (item.pref != index) {
                        val items = Location.values().filter { it.country == prefs.country || it.country == 0 }
                        item.pref = items.elementAt(index).item
                        prefs.locationName = string(items.elementAt(index).textRes)
                        frostEvent("Location", "Count" to Location(index).name)
                    }
                }
            }
        }
        textGetter = {
            string(Location(it).textRes)
        }
    }
}
