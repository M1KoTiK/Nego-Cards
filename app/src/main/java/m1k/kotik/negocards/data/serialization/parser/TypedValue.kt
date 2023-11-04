package m1k.kotik.negocards.data.serialization.parser

import java.lang.reflect.Type
import kotlin.reflect.KType

/**
 * Представляет тип объекта и его значение
 */
class TypedValue(val type: KType, val value: Any)