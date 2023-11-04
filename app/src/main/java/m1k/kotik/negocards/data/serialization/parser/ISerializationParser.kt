package m1k.kotik.negocards.data.serialization.parser

import m1k.kotik.negocards.data.serialization.serializationObject.ISerializationObject
import m1k.kotik.negocards.data.serialization.value_converters.IValueConverterSet
import kotlin.reflect.KClass

interface ISerializationParser {
    /**
     * Показывает должен ли сериализатор учитывать смену типа на Double или Int, чтобы
     * избавиться от необходимость использовать символа начала и окончания значения для свойства
     * сериализуемого объекта
     */
    var checkTypeChange: Boolean
    /**
     * Показывает с объектами какого типа может работать Parser
     *
     * ключ - это key в интерфейсе ISerializationObject
     *
     * значение - это тип класса объекта экземпляр которого будет создаваться при сериалиазации
     */
    val requiredObjectMap: Map<String, KClass<*>>
    /**
     * Представляет набор конвертеров, которые будут
     * выполнять сериализацию и десериализацию для своего типа объекта
     */
    var converterSet: IValueConverterSet

    /**
     * Возвращает сериализованный список объектов разделенных на части (SeparatedObject)
     */
    fun parseString(serializationString: String):List<SeparatedObject>
    /**
     * Метод который разбирает список сериализуемых объектов на части (SeparatedObject)
     */
    fun parseObject(listSObj: List<ISerializationObject>): List<SeparatedObject>
}