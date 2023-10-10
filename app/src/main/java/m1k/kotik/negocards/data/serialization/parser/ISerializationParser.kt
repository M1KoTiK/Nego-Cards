package m1k.kotik.negocards.data.serialization.parser

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet

interface ISerializationParser {
    var checkTypeChange: Boolean
    var serializationStringSplitObjectValueIndex: Int
    var converterSet: IValueConverterSet
    fun parseString(serializationString: String, sObj: ISerializationObject):Map<String, TypedValue>
    fun parseObject(sObj: ISerializationObject): Map<String,TypedValue>
}