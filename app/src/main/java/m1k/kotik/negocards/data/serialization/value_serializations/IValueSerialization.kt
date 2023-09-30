package m1k.kotik.negocards.data.serialization.value_serializations

interface IValueSerialization<T> {
   var serializeDetector: String
   fun serialize(value:T):String
   fun deserialize(serializationValue:String):T
}