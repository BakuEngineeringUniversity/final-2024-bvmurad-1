package com.voluns5.voluns.controller

import com.voluns5.voluns.LoginRequest
import com.voluns5.voluns.model.ResponseWrapper
import com.voluns5.voluns.model.User
import com.voluns5.voluns.repository.UserRepository
import com.voluns5.voluns.service.UserService
import com.voluns5.voluns.service.ValidationService
import jakarta.validation.Valid
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val validationService: ValidationService
) {
    @PostMapping("/createUser")
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<ResponseWrapper<User>> {
        return try {
            if (user.username.isNullOrEmpty()) {
                return ResponseEntity.badRequest().body(
                    ResponseWrapper(
                        status = "error",
                        message = "Username cannot be empty",
                        data = null
                    )
                )
            }
            if (userRepository.existsByUsername(user.username!!)) {
                return ResponseEntity.badRequest().body(
                    ResponseWrapper(
                        status = "error",
                        message = "Username ${user.username} is already taken",
                        data = null
                    )
                )
            }
            if (!validationService.validateUsername(user.username ?: "")) {
                return ResponseEntity.badRequest().body(
                    ResponseWrapper(
                        status = "error",
                        message = "Username must be between ${validationService.config.minUsernameLength} and ${validationService.config.maxUsernameLength} characters long",
                        data = null
                    )
                )
            }
            if (!validationService.validatePassword(user.password ?: "")) {
                return ResponseEntity.badRequest().body(
                    ResponseWrapper(
                        status = "error",
                        message = "Password must be at least ${validationService.config.minPasswordLength} characters long",
                        data = null
                    )
                )
            }
            if (!validationService.validateEmail(user.email ?: "")) {
                return ResponseEntity.badRequest().body(
                    ResponseWrapper(
                        status = "error",
                        message = "Invalid email format",
                        data = null
                    )
                )
            }
            val encoder = BCryptPasswordEncoder()
            user.password = encoder.encode(user.password ?: "")
            val savedUser = userService.createUser(user)
            val response = ResponseWrapper(
                status = "success",
                message = "User created successfully",
                data = savedUser
            )
            ResponseEntity.ok(response)
        } catch (ex: Exception) {
            val response = ResponseWrapper<User>(
                status = "error",
                message = "Failed to create user: ${ex.message}",
                data = null
            )
            ResponseEntity.status(500).body(response)
        }
    }
   /* @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<ResponseWrapper<User>> {
        return try {
            val user = userRepository.findByUsername(loginRequest.username)
            if (user.isPresent && user.get().password == loginRequest.password) {
                val response = ResponseWrapper(
                    status = "success",
                    message = "Login successful",
                    data = user.get()
                )
                ResponseEntity.ok(response)
            } else {
                val response = ResponseWrapper<User>(
                    status = "error",
                    message = "Invalid username or password",
                    data = null
                )
                ResponseEntity.status(401).body(response)
            }
        } catch (ex: Exception) {
            val response = ResponseWrapper<User>(
                status = "error",
                message = "Login failed: ${ex.message}",
                data = null
            )
            ResponseEntity.status(500).body(response)
        }
    }*/

    @PostMapping("/login")
    fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<ResponseWrapper<User>> {
        return try {
            val user = userRepository.findByUsername(loginRequest.username)

            if (user.isPresent && BCryptPasswordEncoder().matches(loginRequest.password, user.get().password)) {
                val response = ResponseWrapper(
                    status = "success",
                    message = "Login successful",
                    data = user.get()
                )
                ResponseEntity.ok(response)
            } else {
                val response = ResponseWrapper<User>(
                    status = "error",
                    message = "Invalid username or password",
                    data = null
                )
                ResponseEntity.status(401).body(response)
            }
        } catch (ex: Exception) {
            val response = ResponseWrapper<User>(
                status = "error",
                message = "Login failed: ${ex.message}",
                data = null
            )
            ResponseEntity.status(500).body(response)
        }
    }



    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): ResponseEntity<ResponseWrapper<User>> {
        return try {
            val user = userService.getUserById(id)
            if (user != null) {
                val response = ResponseWrapper(
                    status = "success",
                    message = "User retrieved successfully",
                    data = user
                )
                ResponseEntity.ok(response)
            } else {
                val response = ResponseWrapper<User>(
                    status = "error",
                    message = "User with id $id not found",
                    data = null
                )
                ResponseEntity.status(404).body(response)
            }
        } catch (ex: Exception) {
            val response = ResponseWrapper<User>(
                status = "error",
                message = "Failed to retrieve user: ${ex.message}",
                data = null
            )
            ResponseEntity.status(500).body(response)
        }
    }


    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User): ResponseEntity<ResponseWrapper<User>> {
        return try {
            val updatedUser = userService.updateUser(id, user)
            if (updatedUser != null) {
                val response = ResponseWrapper(
                    status = "success",
                    message = "User updated successfully",
                    data = updatedUser
                )
                ResponseEntity.ok(response)
            } else {
                val response = ResponseWrapper<User>(
                    status = "error",
                    message = "User with id $id not found",
                    data = null
                )
                ResponseEntity.status(404).body(response)
            }
        } catch (ex: Exception) {
            val response = ResponseWrapper<User>(
                status = "error",
                message = "Failed to update user: ${ex.message}",
                data = null
            )
            ResponseEntity.status(500).body(response)
        }}


        @DeleteMapping("/{id}")
        fun deleteUser(@PathVariable id: Long): ResponseEntity<ResponseWrapper<Unit>> {
            return try {
                val success = userService.deleteUser(id)
                if (success) {
                    val response = ResponseWrapper<Unit>(
                        status = "success",
                        message = "User deleted successfully",
                        data = null
                    )
                    ResponseEntity.ok(response)
                } else {
                    val response = ResponseWrapper<Unit>(
                        status = "error",
                        message = "User with id $id not found",
                        data = null
                    )
                    ResponseEntity.status(404).body(response)
                }
            } catch (ex: Exception) {
                val response = ResponseWrapper<Unit>(
                    status = "error",
                    message = "Failed to delete user: ${ex.message}",
                    data = null
                )
                ResponseEntity.status(500).body(response)
            }
        }
    }