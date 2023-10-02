package m1k.kotik.negocards.data.serialization.value_converters

interface IValueConverter<T> {
   // Символы обозначающие начало и конец значения не должны иметь в себе цифру
   val valueStarts: String
   val valueEnds: String
   fun serialize(value:T):String
   fun deserialize(serializationValue:String):T
}