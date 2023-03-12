package m1k.kotik.negocards.fragments.choiceParametersForQR.parametersForText

import kotlinx.coroutines.flow.StateFlow

interface IParamsQRC {
    val QRCValue: StateFlow<String>
}
