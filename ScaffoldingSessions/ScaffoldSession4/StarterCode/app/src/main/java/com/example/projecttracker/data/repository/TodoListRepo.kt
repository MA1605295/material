package com.example.projecttracker.data.repository

import com.example.projecttracker.data.entity.Project
import com.example.projecttracker.data.entity.Todo
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


object TodoListRepo {

    val db by lazy{ Firebase.firestore}
    val projectDocumentRef by lazy{db.collection("projects")}
    val todoDocumentRef by lazy{db.collection("todos")}
    //TODO () ADD THE DOCUMENT REFERENCE

    suspend fun getProjects(): List<Project> = projectDocumentRef
        .get()
        .await()
        .toObjects(Project::class.java)

    suspend fun addProject(project: Project) {
        val documentId = projectDocumentRef.document().id
        project.id = documentId
        projectDocumentRef.document(documentId).set(project)
    }

    suspend fun updateProject(updatedProject: Project) {
        projectDocumentRef.document(updatedProject.id).set(updatedProject)
    }
    suspend fun deleteProject(project: Project) {
        projectDocumentRef.document(project.id).delete()
    }
    suspend fun getTodoListByProject(projectId: String) = todoDocumentRef
        .whereEqualTo("projectId",projectId)
        .get().await().toObjects(Todo::class.java)

    suspend fun addTodo(todo: Todo) {
        val documentId = todoDocumentRef.document().id
        todo.id = documentId
        todoDocumentRef.document(documentId).set(todo)
    }
    suspend fun deleteTodo(id: String) = todoDocumentRef.document(id).delete()
    suspend fun getTodo(id: String) = todoDocumentRef.document(id).get().await().toObject(Todo::class.java)
}

