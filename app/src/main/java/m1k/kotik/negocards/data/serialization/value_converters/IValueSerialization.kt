package m1k.kotik.negocards.data.serialization.value_converters

interface IValueSerialization<T> {
   fun serialize(value:T):String
   fun deserialize(serializationValue:String):T
}