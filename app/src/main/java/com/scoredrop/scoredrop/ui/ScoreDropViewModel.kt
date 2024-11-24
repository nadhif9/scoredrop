package com.scoredrop.scoredrop.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scoredrop.scoredrop.data.DataUser
import com.scoredrop.scoredrop.data.Team
import com.scoredrop.scoredrop.network.ScoreDropApi
import kotlinx.coroutines.launch
import java.io.IOException


sealed interface ScoreUIState {
    data class Success(val listnilai : List<DataUser>) : ScoreUIState
    object Loading : ScoreUIState
    object Error : ScoreUIState
}

sealed interface TeamUIState {
    data class Success(val listmember : List<Team>) : TeamUIState
    object Loading : TeamUIState
    object Error : TeamUIState
}

sealed interface LoggedState{
    object Login : LoggedState
    object Home : LoggedState
}

sealed interface ContentState{
    object Home : ContentState
    object Team : ContentState
    object Settings : ContentState
    object Bobot : ContentState
    object Presensi : ContentState
    object About : ContentState
}

class ScoreDropViewModel : ViewModel() {
    var scoreUIState:ScoreUIState by mutableStateOf(ScoreUIState.Loading)
        private set

    var teamUIState:TeamUIState by mutableStateOf(TeamUIState.Loading)

    var loggedState:LoggedState by mutableStateOf(LoggedState.Login)
    var contentState:ContentState by mutableStateOf(ContentState.Home)
    var inputNim:String by mutableStateOf("")
    var homeButtonState: ImageVector by mutableStateOf(Icons.Filled.Home)
    var teamButtonState: ImageVector by mutableStateOf(Icons.Outlined.Person)
    var settingButtonState: ImageVector by mutableStateOf(Icons.Outlined.Settings)


    init {
        getNilaiMahasiswa("")
    }

    fun getNilaiMahasiswa(nim:String){
        if(nim.length == 14){
        viewModelScope.launch {
            scoreUIState = try {
                val listResult = ScoreDropApi.retrofitService.getNilai(nim = nim)
                ScoreUIState.Success(listnilai = listResult)
            } catch (e: IOException) {
                ScoreUIState.Error
            }
        }
        }
    }

    fun getTeam(team:String){
            viewModelScope.launch {
                teamUIState = try {
                    val listResult = ScoreDropApi.retrofitService.getTeam(team = team)
                    TeamUIState.Success(listmember = listResult)
                } catch (e: IOException) {
                    TeamUIState.Error
                }
            }
    }

}