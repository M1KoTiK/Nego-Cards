package m1k.kotik.negocards.data.serialization.value_converters

import m1k.kotik.negocards.data.serialization.reflection.getType
import java.util.Arrays
import kotlin.reflect.full.createType


class SimpleListConverter(
    override val valueStarts: String,
    override val valueEnds: String,
    private val itemSeparator: String = ","): IValueConverter<MutableList<Any>>
{
    init{

        if(itemSeparator == valueEnds || itemSeparator == valueStarts){
            throw Exception("Чел ты тупой? Значения для начала и конца списка" +
                    " и для разделения их элементов не должны быть одинаковыми")
        }
    }

    override fun deserialize(serializationValue: String): MutableList<Any> {
        val outputList = mutableListOf<Any>()
        val list = serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).split(",")
        for(item in list){
            outputList.add(item)
        }
        return outputList
    }

    override fun serialize(value: MutableList<Any>): String {
        var outputString = valueStarts
        for(item in value){
            if(outputString.length == valueStarts.length){
                outputString += item
            }
            else{
                outputString += ",${item}"
            }
        }
        outputString += valueEnds
        return  outputString
    }

}