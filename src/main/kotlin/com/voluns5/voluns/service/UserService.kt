package com.voluns5.voluns.service

import com.voluns5.voluns.model.User
import com.voluns5.voluns.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun createUser(user: User): User {
        logger.info("Attempting to create user with username: {} and email: {}", user.username, user.email)
        val savedUser = userRepository.save(user)
        logger.info("User created successfully with ID: {} and username: {}", savedUser.id, savedUser.username)
        return savedUser
    }

    fun getAllUsers(): List<User> {
        logger.info("Retrieving all users")
        val users = userRepository.findAll()
        logger.info("Retrieved {} users", users.size)
        return users
    }

    fun getUserById(id: Long): User? {
        logger.info("Searching for user with ID: {}", id)
        val user = userRepository.findById(id).orElse(null)
        if (user != null) {
            logger.info("User found with ID: {} and username: {}", id, user.username)
        } else {
            logger.warn("User not found with ID: {}", id)
        }
        return user
    }

    fun updateUser(id: Long, user: User): User? {
        logger.info("Attempting to update user with ID: {}", id)
        return if (userRepository.existsById(id)) {
            val updatedUser = userRepository.save(user.copy(id = id))
            logger.info("User updated successfully with ID: {} and username: {}", id, updatedUser.username)
            updatedUser
        } else {
            logger.warn("User not found with ID: {}. Update failed.", id)
            null
        }
    }

    fun deleteUser(id: Long): Boolean {
        logger.info("Attempting to delete user with ID: {}", id)
        val user = userRepository.findById(id).orElse(null)
        return if (user != null) {
            userRepository.deleteById(id)
            logger.info("User deleted successfully with ID: {} and username: {}", id, user.username)
            true
        } else {
            logger.warn("User not found with ID: {}. Deletion failed.", id)
            false
        }
    }
}

















/*
@Service
class UserService(private val userRepository: UserRepository) {

    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    // Create user
    fun createUser(user: User): User {
        logger.info("Attempting to create user with username: ${user.username}")
        val savedUser = userRepository.save(user)
        logger.info("User created successfully with ID: ${savedUser.id}")
        return savedUser
    }

    // Get all users
    fun getAllUsers(): List<User> {
        logger.info("Retrieving all users")
        val users = userRepository.findAll()
        logger.info("Retrieved ${users.size} users")
        return users
    }

    // Get user by ID
    fun getUserById(id: Long): User? {
        logger.info("Searching for user with ID: $id")
        val user = userRepository.findById(id).orElse(null)
        if (user != null) {
            logger.info("User found with ID: $id")
        } else {
            logger.warn("User not found with ID: $id")
        }
        return user
    }

    // Update user
    fun updateUser(id: Long, user: User): User? {
        logger.info("Attempting to update user with ID: $id")
        return if (userRepository.existsById(id)) {
            val updatedUser = userRepository.save(user.copy(id = id))
            logger.info("User updated successfully with ID: $id")
            updatedUser
        } else {
            logger.warn("User not found with ID: $id. Update failed.")
            null
        }
    }

    // Delete user
   fun deleteUser(id: Long): Boolean {
        logger.info("Attempting to delete user with ID: $id")
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            logger.info("User deleted successfully with ID: $id")
            true
        } else {
            logger.warn("User not found with ID: $id. Deletion failed.")
            false
        }
    }
}
*/