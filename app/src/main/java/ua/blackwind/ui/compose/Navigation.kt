package ua.blackwind.ui.compose

import androidx.navigation.NavController
import com.ramcosta.composedestinations.spec.Direction


fun NavController.navigate(destination: Direction) {
    this.navigate(destination.route)
}