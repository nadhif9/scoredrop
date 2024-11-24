package com.scoredrop.scoredrop.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scoredrop.scoredrop.R
import com.scoredrop.scoredrop.data.DataUser
import com.scoredrop.scoredrop.ui.theme.ScoredropTheme


@Composable
fun AppScreen() {
    val scoredropViewModel: ScoreDropViewModel = viewModel()
    val state = scoredropViewModel.scoreUIState
    val teamState = scoredropViewModel.teamUIState
    when(scoredropViewModel.loggedState) {
        LoggedState.Login -> loginScreen (svm = scoredropViewModel)
        LoggedState.Home -> HomeScreen(state = state, svm = scoredropViewModel, teamState = teamState)
    }
}


@Composable
fun HomeScreen(
    state:ScoreUIState,
    svm:ScoreDropViewModel,
    teamState:TeamUIState
){
    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
            ) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize()) {

                    Column(modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (svm.homeButtonState == Icons.Outlined.Home) {
                                svm.homeButtonState = Icons.Filled.Home
                                svm.teamButtonState = Icons.Outlined.Person
                                svm.settingButtonState = Icons.Outlined.Settings

                                svm.contentState = ContentState.Home
                            }
                        }
                    ) {
                        Icon(
                            imageVector = svm.homeButtonState,
                            contentDescription = null,
                            tint = if(svm.homeButtonState == Icons.Filled.Home){
                                MaterialTheme.colorScheme.primary
                            }else{MaterialTheme.colorScheme.onSurface},
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )
                        Text("Home", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxWidth())
                    }

                    Column(modifier= Modifier
                        .weight(1f)
                        .clickable {
                            if (svm.teamButtonState == Icons.Outlined.Person) {
                                svm.teamButtonState = Icons.Filled.Person
                                svm.homeButtonState = Icons.Outlined.Home
                                svm.settingButtonState = Icons.Outlined.Settings

                                svm.contentState = ContentState.Team
                            }
                        }
                    ) {
                        Icon(
                            imageVector = svm.teamButtonState,
                            contentDescription = null,
                            tint = if(svm.teamButtonState == Icons.Filled.Person){
                                MaterialTheme.colorScheme.primary
                            }else{MaterialTheme.colorScheme.onSurface},
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )
                        Text("Kelompok", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxWidth())
                    }

                    Column(modifier = Modifier
                        .weight(1f)
                        .clickable {
                            if (svm.settingButtonState == Icons.Outlined.Settings) {
                                svm.settingButtonState = Icons.Filled.Settings
                                svm.teamButtonState = Icons.Outlined.Person
                                svm.homeButtonState = Icons.Outlined.Home

                                svm.contentState = ContentState.Settings
                            }
                        }
                    ) {
                        Icon(
                            imageVector = svm.settingButtonState,
                            contentDescription = null,
                            tint = if(svm.settingButtonState == Icons.Filled.Settings){
                                MaterialTheme.colorScheme.primary
                            }else{MaterialTheme.colorScheme.onSurface},
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f)
                        )
                        Text("Settings", textAlign = TextAlign.Center, color = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxWidth())
                    }

                }
            }
        },

    ) { innerPadding ->
        when(state) {
            is ScoreUIState.Loading -> loadingScreen(modifier = Modifier.fillMaxSize())
            is ScoreUIState.Success ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    if(state.listnilai[0].result == "success") {
                        when(svm.contentState) {
                            ContentState.Home -> NilaiList(svm=svm, listnilai = state.listnilai, modifier = Modifier.weight(1f))
                            ContentState.Team -> TeamList(svm = svm, teamState = teamState, team = state.listnilai[0].kelompok)
                            ContentState.Settings -> SettingPage(svm = svm)
                            ContentState.Bobot -> PageBobot(svm = svm)
                            ContentState.Presensi -> TeamListPresensi(svm = svm, teamState = teamState, team = state.listnilai[0].kelompok)
                            ContentState.About -> AboutPage(svm = svm)
                        }
                    }else{
                        errorScreen("NIM TIDAK DITEMUKAN", svm = svm , result = state.listnilai[0], modifier = Modifier.weight(1f))
                    }
                }
            is ScoreUIState.Error -> errorScreen("JARINGAN BERMASALAH", svm = svm)
        }
    }
}

@Composable
fun TeamList(svm:ScoreDropViewModel, teamState: TeamUIState, team:String, modifier: Modifier = Modifier){
    if(teamState !is TeamUIState.Success) {
        svm.getTeam(team)
    }
    when(teamState){
        is TeamUIState.Success ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                ) {

                    for (member in teamState.listmember) {
                        if (member.nama != "") {
                            Box(modifier = Modifier
                                .weight(1f)
                                .padding(5.dp)
                            ) {
                                UserCard(
                                    name = member.nama,
                                    nim = member.nim,
                                    presensi = member.presensi,
                                    svm = svm
                                )
                            }
                        }
                    }
                }
        is TeamUIState.Loading -> loadingScreen()
        is TeamUIState.Error -> errorScreen(svm = svm)
            
    }
}

@Composable
fun TeamListPresensi(svm:ScoreDropViewModel, teamState: TeamUIState, team:String, modifier: Modifier = Modifier){
    if(teamState !is TeamUIState.Success) {
        svm.getTeam(team)
    }
    when(teamState){
        is TeamUIState.Success ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {

                for (member in teamState.listmember) {
                    if (member.nama != "") {
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(5.dp)
                        ) {
                            UserCardPresensi(
                                name = member.nama,
                                nim = member.nim,
                                presensi = member.presensi,
                                svm = svm
                            )
                        }
                    }
                }
            }
        is TeamUIState.Loading -> loadingScreen()
        is TeamUIState.Error -> errorScreen(svm = svm)

    }
}

@Composable
fun Title(modifier: Modifier = Modifier){
    Text(text = "ScoreDrop", modifier = Modifier
        .fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = typography.displayLarge
    )
}

@Composable
fun NilaiList(svm: ScoreDropViewModel,listnilai : List<DataUser>,modifier: Modifier = Modifier){
    val listnama : List<String> = listOf("TP", "PRAKTIKUM", "LA", "TUGAS", "TOTAL")
    Column(
        modifier = Modifier
            .padding(2.dp)
            .background(MaterialTheme.colorScheme.surface)
    ) {

        IDCard(svm=svm, name = listnilai[0].nama, nim = listnilai[0].nim, kelompok = listnilai[0].kelompok, modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        )

        Row(modifier = Modifier
            .weight(1f)
    ) {
            NilaiCard(
                title = listnama[0],svm=svm, nilai = listnilai[0].tp, modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
            NilaiCard(
                title = listnama[1],svm=svm, nilai = listnilai[0].praktikum, modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
        }

        Row(
            modifier = Modifier.weight(1f)
        ) {
            NilaiCard(
                title = listnama[2],svm=svm, nilai = listnilai[0].la, modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
            NilaiCard(
                title = listnama[3],svm=svm, nilai = listnilai[0].tugas, modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
        }
        NilaiCard(title = listnama[4],svm=svm, nilai = listnilai[0].total, modifier = Modifier
            .weight(1f)
            .padding(5.dp)
        )
    }
    }



@Composable
fun IDCard(svm: ScoreDropViewModel,name:String, nim:String, kelompok:String, modifier:Modifier = Modifier) {
    OutlinedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                svm.contentState = ContentState.Team
                svm.teamButtonState = Icons.Filled.Person
                svm.homeButtonState = Icons.Outlined.Home
            }
    ) {
        Row() {
            Column(modifier = Modifier.weight(0.8f)) {

                Row(
                    modifier = Modifier
                        .weight(0.7f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)

                ) {
                    Text(
                        text = name,
                        style = when (name.length) {
                            in 1..10 -> MaterialTheme.typography.displayMedium
                            in 11..20 -> MaterialTheme.typography.displaySmall
                            else -> MaterialTheme.typography.titleLarge
                        },
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 5.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    )
                }
                HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.primary)
                Row(
                    modifier = Modifier
                        .weight(0.3f)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)

                ) {
                    Text(
                        text = nim,
                        style = typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.ExtraBold,
                        letterSpacing = 5.sp,
                        modifier = modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            Row(
                modifier = Modifier
                    .weight(0.2f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = kelompok,
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = when (kelompok.length) {
                        in 1..2 -> MaterialTheme.typography.displayLarge
                        else -> MaterialTheme.typography.displayMedium
                    },
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 10.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }
        }
    }
}

@Composable
fun UserCard(name: String, svm:ScoreDropViewModel,nim: String,presensi: String, modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                svm.contentState = ContentState.Presensi
                svm.teamButtonState = Icons.Outlined.Person
            }
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .weight(0.7f)
        ) {
            Text(
                text = name,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displaySmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .weight(0.7f)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            )
            Box(modifier = Modifier
                .weight(0.3f)
                .fillMaxSize()
                .background(
                    color = when (presensi) {
                        "H" -> MaterialTheme.colorScheme.primary
                        "T" -> MaterialTheme.colorScheme.tertiary
                        "A" -> MaterialTheme.colorScheme.error
                        "I" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            ) {

                Icon(imageVector = when(presensi) {
                    "A" -> Icons.Filled.Close
                    "T" -> Icons.Filled.Check
                    "H" -> Icons.Filled.Check
                    "I" -> Icons.Filled.Check
                    else -> Icons.Filled.Check
                }
                    , contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = when(presensi) {
                        "H" -> MaterialTheme.colorScheme.onPrimary
                        "T" -> MaterialTheme.colorScheme.onTertiary
                        "A" -> MaterialTheme.colorScheme.onError
                        "I" -> MaterialTheme.colorScheme.onError
                        else -> MaterialTheme.colorScheme.inversePrimary
                    })
            }
        }

    }
}

@Composable
fun UserCardPresensi(name: String, svm: ScoreDropViewModel, nim: String,presensi: String){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.extraLarge,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                svm.contentState = ContentState.Team
                svm.teamButtonState = Icons.Filled.Person
            }
    ) {
        Row(modifier = Modifier
        ) {
            Text(
                text = nim,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.7f)
                    .align(Alignment.CenterVertically)
            )
            Box(modifier = Modifier
                .weight(0.3f)
                .fillMaxSize()
                .background(
                    color = when (presensi) {
                        "H" -> MaterialTheme.colorScheme.primary
                        "T" -> MaterialTheme.colorScheme.tertiary
                        "A" -> MaterialTheme.colorScheme.error
                        "I" -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.inversePrimary
                    }
                )
            ) {


                Text(
                    text = presensi,
                    style = MaterialTheme.typography.displayLarge,
                    color =
                    when(presensi) {
                        "H" -> MaterialTheme.colorScheme.onPrimary
                        "T" -> MaterialTheme.colorScheme.onTertiary
                        "A" -> MaterialTheme.colorScheme.onError
                        "I" -> MaterialTheme.colorScheme.onError
                        else -> MaterialTheme.colorScheme.inversePrimary
                    },
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }

    }
}

@Composable
fun NilaiCard(title: String, svm: ScoreDropViewModel, nilai: String, modifier: Modifier = Modifier){
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = MaterialTheme.shapes.extraSmall,
        modifier = modifier
            .fillMaxSize()
            .clickable {
                if (svm.contentState is ContentState.Home) {
                    svm.contentState = ContentState.Bobot
                    svm.homeButtonState = Icons.Outlined.Home
                } else {
                    svm.contentState = ContentState.Home
                    svm.homeButtonState = Icons.Filled.Home
                }
            }
    ){
            Row(
                modifier = Modifier
                    .weight(0.3f)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)

            ) {
                Text(
                    text = title,
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = if(title != "TUGAS PENDAHULUAN"){
                        5.sp
                    }else{
                        1.sp
                    },
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }
            Row(
                modifier = Modifier
                    .weight(0.7f)
                    .background(MaterialTheme.colorScheme.surface)
                    .fillMaxSize()
            ) {
                Text(
                    text = nilai.toString(),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = typography.displayLarge,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = 10.sp,
                    modifier = modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically)
                )
            }
    }
}

@Composable
fun loginScreen(svm:ScoreDropViewModel,modifier: Modifier = Modifier){

    val infiniteTransition = rememberInfiniteTransition(label = "login")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(tween(1000), RepeatMode.Reverse)
    )
    Box(
        modifier = Modifier.background(
            MaterialTheme.colorScheme.surface
        )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier
                .fillMaxSize()
                .padding(top = 250.dp, bottom = 200.dp, start = 20.dp, end = 20.dp)
        ) {
            Text(
                text = "ScoreDrop",
                style = typography.displayLarge,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 100.dp)
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        transformOrigin = TransformOrigin.Center
                    }
            )
            Spacer(modifier.height(50.dp))
            OutlinedTextField(
                value = svm.inputNim,
                label = {
                    Text("NIM")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    svm.inputNim = it;
                    if (it.length == 14) {
                        svm.loggedState = LoggedState.Home;
                        svm.getNilaiMahasiswa(it)
                    }

                }, modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.background,
                    focusedContainerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    }
}

@Composable
fun loadingScreen(modifier: Modifier = Modifier){

Box( modifier = Modifier.fillMaxSize()) {
    CircularProgressIndicator(
        modifier = Modifier
            .align(Alignment.Center)
            .width(100.dp)
            .height(100.dp)
        ,
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}
}

@Composable
fun errorScreen(msg:String = "", svm:ScoreDropViewModel, result: DataUser = DataUser(),modifier: Modifier = Modifier){
    Column(modifier = Modifier
        .fillMaxSize()
        .clickable {
            if (result.result == "tidak ditemukan") {
                svm.loggedState = LoggedState.Login
            } else {
                svm.getNilaiMahasiswa(svm.inputNim)
            }
        }
        ,
    ) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = 250.dp)
            ,
            painter = painterResource(R.drawable.error),
            contentDescription = "Loading",
        )
        Text(text=msg,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 300.dp)
            ,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.ExtraBold
            )
    }
}

@Composable
fun PageBobot(svm: ScoreDropViewModel){
    val listnama : List<String> = listOf("TUGAS PENDAHULUAN", "NILAI PRAKTIKUM", "LEMBAR ANALISIS", "NILAI TUGAS", "  NILAI \n TOTAL")
    val listbobot : List<String> = listOf("10%", "20%", "30%", "40%", "100%")
    Column(
        modifier = Modifier
            .padding(2.dp)
    ) {

        Row(modifier = Modifier
            .weight(1f)
        ) {
            NilaiCard(
                title = listnama[0],svm=svm, nilai = listbobot[0], modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
            NilaiCard(
                title = listnama[1],svm=svm, nilai = listbobot[1], modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
        }

        Row(
            modifier = Modifier.weight(1f)
        ) {
            NilaiCard(
                title = listnama[2],svm=svm, nilai = listbobot[2], modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
            NilaiCard(
                title = listnama[3],svm=svm, nilai = listbobot[3], modifier = Modifier
                    .padding(5.dp)
                    .weight(1f)
            )
        }
    }
}

@Composable
fun SettingPage(svm: ScoreDropViewModel){
    var logoutwarning by remember { mutableStateOf(false) }
    if(logoutwarning == true){

        AlertDialog(
            title = {
                Text(text = "Keluar dari aplikasi?", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
            },
            onDismissRequest = {
                logoutwarning = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                svm.loggedState = LoggedState.Login
                svm.settingButtonState = Icons.Outlined.Settings
                svm.homeButtonState = Icons.Filled.Home
                svm.contentState = ContentState.Home
                svm.inputNim = ""

                svm.teamUIState = TeamUIState.Loading
                    }
                ) {
                    Text("Keluar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        logoutwarning = false
                    }
                ) {
                    Text("Kembali")
                }
            }
        )

    }
    Column {
        Text("Settings", style=MaterialTheme.typography.displayLarge , color=MaterialTheme.colorScheme.onSurface,textAlign = TextAlign.Center, modifier = Modifier
            .fillMaxWidth()
        )

            Column(modifier = Modifier
                .weight(0.5f)
                .fillMaxSize()
                .clickable {
                    svm.settingButtonState = Icons.Outlined.Settings
                    svm.contentState = ContentState.About
                }
            ) {
                Icon(imageVector = Icons.Filled.Info,
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = "about",
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.8f)
                        .padding(top = 5.dp)
                )
                Text("About",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.displayMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2f)
                )
            }

        Column(modifier = Modifier
            .weight(0.5f)
            .fillMaxSize()
            .clickable {
                logoutwarning = true
            }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                tint = MaterialTheme.colorScheme.onSurface,
                contentDescription = "exit",
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.8f)
                    .padding(top = 5.dp)
            )
            Text("Exit",
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(0.2f)
            )
        }

        }



}

@Composable
fun AboutPage(svm: ScoreDropViewModel){
    Column {
        Text(text = "About", style = MaterialTheme.typography.displayLarge, modifier = Modifier.fillMaxWidth())
        HorizontalDivider(thickness = 3.dp, color = MaterialTheme.colorScheme.primary)
        Text(text = "Scoredrop adalah aplikasi yang dibuat dengan fitur utama untuk menampilkan nilai-nilai dari praktikum, selain fitur utama tersebut terdapat fitur lain yang berguna untuk memudahkan mendapat informasi yang terkait dengan praktikum maka ditambahkan juga fitur untuk mengecek bobot dari nilai praktikum, nomor kelompok, anggota kelompok, dan status kehadiran.",
            style = MaterialTheme.typography.titleLarge
            )
    }

}

@Preview
@Composable
fun AppScreenPreview(){
    ScoredropTheme {
        AppScreen()
    }
}