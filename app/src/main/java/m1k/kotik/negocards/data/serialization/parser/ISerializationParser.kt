package m1k.kotik.negocards.data.serialization.parser

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import kotlin.reflect.KClass

interface ISerializationParser {
    var checkTypeChange: Boolean
    val requiredObjectMap: Map<String, KClass<*>>
    var converterSet: IValueConverterSet
    fun parseString(serializationString: String):List<SeparatedObject>
    fun parseObject(listSObj: List<ISerializationObject>): List<SeparatedObject>
}