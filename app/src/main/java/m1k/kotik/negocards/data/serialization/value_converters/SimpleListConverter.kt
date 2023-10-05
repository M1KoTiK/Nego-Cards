package m1k.kotik.negocards.data.serialization.value_converters

import m1k.kotik.negocards.data.serialization.reflection.getType
import java.util.Arrays
import kotlin.reflect.full.createType



class SimpleListConverter<T>(
    override val valueStarts: String,
    override val valueEnds: String,
    private val itemSeparator: String = ","): IValueConverter<MutableList<T>>
{
    init{

        if(itemSeparator == valueEnds || itemSeparator == valueStarts){
            throw Exception("Чел ты тупой? Значения для начала и конца списка" +
                    " и для разделения их элементов не должны быть одинаковыми")
        }
    }
    @Suppress("UNCHECKED_CAST")
    override fun deserialize(serializationValue: String): MutableList<T> {
        val outputList = mutableListOf<T>()
        val list = serializationValue.drop(valueStarts.length).dropLast(valueEnds.length).split(itemSeparator)
        for(item in list){
            outputList.add(item as T)
        }
        return outputList
    }

    override fun serialize(value: MutableList<T>): String {
        var outputString = valueStarts
        for(item in value){
            if(outputString.length == valueStarts.length){
                outputString += item
            }
            else{
                outputString += "${itemSeparator}${item}"
            }
        }
        outputString += valueEnds
        println(outputString)
        return  outputString
    }

}