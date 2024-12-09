@file:Suppress("JpaDataSourceORMInspection")

package com.voluns5.voluns.model

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @NotBlank
    @Size(min = 3, max = 50)
    var username: String? = null,

    @NotBlank
    @Email
    var email: String? = null,

    @NotBlank
    var name: String? = null,

    @NotBlank
    @Size(min = 6)
    var password: String? = null
) {
    constructor() : this(null, null, null, null, null) // Parametresiz constructor
}
