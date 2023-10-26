package org.sopt.dosopttemplate.presentation.main.home

import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.presentation.model.HomeModel
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    val friendInfoList = listOf(
        HomeModel.FriendInfoModel(1, "Alice", LocalDate.of(1990, 5, 15), "Pop"),
        HomeModel.FriendInfoModel(2, "Bob", LocalDate.of(1988, 8, 22), "Rock"),
        HomeModel.FriendInfoModel(3, "Charlie", LocalDate.of(1995, 3, 10), "Jazz"),
        HomeModel.FriendInfoModel(4, "David", LocalDate.of(1992, 11, 5), "Hip-Hop"),
        HomeModel.FriendInfoModel(5, "Eve", LocalDate.of(1987, 7, 2), "Classical"),
        HomeModel.FriendInfoModel(6, "Frank", LocalDate.of(1993, 4, 18), "R&B"),
        HomeModel.FriendInfoModel(7, "Grace", LocalDate.of(1991, 9, 30), "Country"),
        HomeModel.FriendInfoModel(8, "Hank", LocalDate.of(1985, 12, 8), "Electronic"),
        HomeModel.FriendInfoModel(9, "Ivy", LocalDate.of(1989, 6, 25), "Rock"),
        HomeModel.FriendInfoModel(10, "Jack", LocalDate.of(1998, 1, 14), "Jazz"),
        HomeModel.FriendInfoModel(11, "Karen", LocalDate.of(1983, 2, 9), "Pop"),
        HomeModel.FriendInfoModel(12, "Leo", LocalDate.of(1997, 10, 24), "Hip-Hop"),
        HomeModel.FriendInfoModel(13, "Mary", LocalDate.of(1986, 8, 27), "Classical"),
        HomeModel.FriendInfoModel(14, "Nathan", LocalDate.of(1996, 7, 16), "R&B"),
        HomeModel.FriendInfoModel(15, "Olivia", LocalDate.of(1984, 3, 21), "Country"),
        HomeModel.FriendInfoModel(16, "Peter", LocalDate.of(1994, 12, 4), "Electronic"),
        HomeModel.FriendInfoModel(17, "Quincy", LocalDate.of(1999, 4, 3), "Rock"),
        HomeModel.FriendInfoModel(18, "Rachel", LocalDate.of(1982, 6, 11), "Jazz"),
        HomeModel.FriendInfoModel(19, "Sam", LocalDate.of(1981, 9, 17), "Pop"),
        HomeModel.FriendInfoModel(20, "Tom", LocalDate.of(1992, 5, 29), "Hip-Hop"),
        HomeModel.FriendInfoModel(21, "Ursula", LocalDate.of(1990, 11, 19), "Classical"),
        HomeModel.FriendInfoModel(22, "Victor", LocalDate.of(1995, 2, 28), "R&B"),
        HomeModel.FriendInfoModel(23, "Wendy", LocalDate.of(1987, 10, 24), "Country"),
        HomeModel.FriendInfoModel(24, "Xander", LocalDate.of(1996, 3, 7), "Electronic"),
        HomeModel.FriendInfoModel(25, "Yvonne", LocalDate.of(1988, 7, 13), "Rock"),
        HomeModel.FriendInfoModel(26, "Zane", LocalDate.of(1993, 1, 12), "Jazz"),
        HomeModel.FriendInfoModel(27, "Amy", LocalDate.of(1984, 4, 9), "Pop"),
        HomeModel.FriendInfoModel(28, "Brian", LocalDate.of(1998, 8, 16), "Hip-Hop"),
        HomeModel.FriendInfoModel(29, "Cathy", LocalDate.of(1986, 12, 23), "Classical"),
        HomeModel.FriendInfoModel(30, "Daniel", LocalDate.of(1991, 3, 5), "R&B")
    )
}