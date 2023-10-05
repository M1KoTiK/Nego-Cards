package m1k.kotik.negocards.data.serialization.value_converters

import kotlin.reflect.KType

interface IValueConverter<T> {
   // Символы обозначающие начало и конец значения не должны иметь в себе цифру
   val valueStarts: String
   val valueEnds: String
   fun serialize(value: Any):String
   fun deserialize(serializationValue:String):T
}