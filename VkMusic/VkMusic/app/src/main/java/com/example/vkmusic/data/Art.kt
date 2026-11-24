package com.example.vkmusic.data

import com.example.vkmusic.R

/**
 * Картинки из макета. В макете это временные ссылки googleusercontent (aida) — они могут
 * перестать открываться. Для продакшена замените на URL вашего бэкенда/CDN
 * или положите файлы в res/drawable-nodpi и используйте R.drawable.*.
 *
 * Тип [Any]: Coil принимает и String (URL), и Int (@DrawableRes).
 */
typealias ImageModel = Any

object Art {
    const val MumiTroll: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrALAiUBjzCQcvQRLhdXiQKctx41YDAaNvDLrUlo2xNlElAxCg07sFINk3c1NybIHHJE5UNjBLs5jVsFjttsyZTVgZZ_mL1v8bYig82gzspQpP5_hKzkXxJ7IOvLzzQXh4vPo7d5W9vujCzVYxGBWsoRuUpo4IKHMPynr5x7knmUi1Y2jxoiEtWubjXKtb5oi0aMBQXocBJzjeyU7giDdr_Kk-hjrWmhXtrnQWPnxNeji5bSE_ECpS03UF0"

    const val BigBabyTape: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrBBWHRhEdKKy6b4XJyahPtlG6dAkeVyH66Nd8yBWqWToV0VIRXQcCla5oemSBIjinwbBldE2WZbDeQMbV4ZycC4G7AbatwDxlcXGKcFVfWWV3Z7qagdIN2RL-dNKWH3af1oSX_YpH8Zxf767c6lvUsRlVDNgweeeEyaUinXsh91zVRAdlXpejOCJ2XycvXtdsmffdnKDPAidITp-ZM_GQ3e4_XDCRHGFPVFycLcD3yMchA9qVIMo7ejCQIQ"

    const val Miyagi: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrBujMGTGMuzpA5qtCZVYbnwZ5NeD7vfyN3sxftg42ReorVKpI3px7B6DVWydJy5h4nyogTp3NH-OuWUeiwW9gR17kwa7ahJ07kbVOkjck_nbOeG89d3PNFkeALLwRcvBP18rw53VLquHlLQOpbrqRxCfZM0maxIeAzkLa8OkCXpP22jDVZ7qluR8Y82FuijxL-Ouo2uvDRXw0pSy2qEHn9X4VW-Ho3v6O3foH28Cv3yj7IJe9D6LOa9oxeHaw"

    const val Aria: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrC8GTu8I-DLX5Kf9txud4DtVriss5lOg25rWb799RRNVHhvJ_tFqPqJS6ia09Y4uUHzKuNR1eDcjCUxMTkSPrscUNhfCJXypzBGP3Q0SMC9s5Kb2VuaMZG-ECI0ZaePJwaDf8o8TUwZvoR9QubRfLHEl2qQbsHCOLhlB30K8orTwZWMUA486ZUQDbWVwYfsEuuyzCsiVCTWA7LmXaontyeh7dOMfeJ6M5vt6PcCVLGA1-x_ycWoHR4bFw"

    const val Cover5: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrCjyOawGsWK_O0MecpjDchJt8X9DNgPfteS8VWbfJ1vxqWi35t0D34PyET341g3FiIrASItVhLhK5LnYASpdg0V-TL7hUsA2mKX8RMW0rV_h86rkmCrENjikYdyrLYe6yCG1H4SlB3-RN73vPIBw8XwTnGCV-xCm0XqIyd-btKhxIhK5vjc1vMA2N_21W9NM1oR6n37N0EGK3SjYoeTMHHh4TgoYiRD41vgrJgPkKEZWu1OG7QfrBkHJGY"

    const val Cover6: String =
        "https://lh3.googleusercontent.com/aida/ALJJyrAieflBatSRLUbBj3832brMUY5bFpoDeH_2NdWW4yW6NzwcqxX1E2Pa4elXvgOnkS4aThaEqM1DbrlTfHnefLJAh6uDLnXC48xLeAE80nPN_msPxQOv-e_blsDJiwBpQ06oHOlq8BF8rvTEffw7Pge0rL0Unbn2rDlackJnMKS7lejgosp3Nac4hHtRy4LOMCoEmJF-IXxYTy1V4un51HvnEJWTsMhDwfUwVrhhbEcr4B9AmGuWyJzjL4A"

    /** Встроенное в макет base64-изображение, сохранено как res/drawable-nodpi/art_instasamka.jpg */
    val Instasamka: ImageModel = R.drawable.art_instasamka
}
