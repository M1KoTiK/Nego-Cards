package m1k.kotik.negocards.data.serialization.string_utils

fun findRestrictedBetween(inputString: String, startBound: String, endBound: String): String?{
    val startIndex = inputString.indexOf(startBound)
    var endIndex: Int = -1
    if(startBound == endBound){
        endIndex = inputString.indexOf(endBound, startIndex + startBound.length)
    }
    else{
        endIndex = inputString.indexOf(endBound)
    }

    if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
        return inputString.substring(startIndex , endIndex + 1)
    } else {
        return null
    }
}