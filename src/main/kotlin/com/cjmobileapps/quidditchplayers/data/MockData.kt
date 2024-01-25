package com.cjmobileapps.quidditchplayers.data

import com.cjmobileapps.quidditchplayers.data.model.House
import com.cjmobileapps.quidditchplayers.data.model.HouseName
import com.cjmobileapps.quidditchplayers.data.model.Position

object MockData {

    val positions = mapOf(
        1 to Position(positionName = "Chaser"),
        2 to Position(positionName = "Beater"),
        3 to Position(positionName = "Keeper"),
        4 to Position(positionName = "Seeker")
    )

    val houses = listOf(
        House(
            name = HouseName.GRYFFINDOR,
            imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/98/Gryffindor.jpg/revision/latest",
            emoji = "\uD83E\uDD81"
        ),
        House(
            name = HouseName.SLYTHERIN,
            imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/6/6e/Slytherin.jpg/revision/latest",
            emoji = "\uD83D\uDC0D"
        ),
        House(
            name = HouseName.RAVENCLAW,
            imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/3/3c/RavenclawCrest.jpg/revision/latest",
            emoji = "\uD83E\uDD85"
        ),
        House(
            name = HouseName.HUFFLEPUFF,
            imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e4/Hufflepuff.jpg/revision/latest",
            emoji = "\uD83E\uDDA1"
        )
    )
}

//todo add etags only to player calls becuse they have status updates
//todo other api call cache control max age 60 seconds