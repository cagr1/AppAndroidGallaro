package com.gallardoromero.task1

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import  android.widget.EditText
import android.text.InputType
import com.google.android.material.textfield.TextInputEditText


class MainActivity : AppCompatActivity() {

    // Declara etMultilineInput como una propiedad de la clase
    private lateinit var etMultilineInput: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        // Inicializa etMultilineInput correctamente
        etMultilineInput = findViewById<TextInputEditText>(R.id.etMultilineInput)

        // Vincula los botones
        val btnDecimalExpression = findViewById<MaterialButton>(R.id.btnDecimalExpression)
        val btnAnalyzeValues = findViewById<MaterialButton>(R.id.btnAnalyzeValues)
        val btnMultiplicationTable = findViewById<MaterialButton>(R.id.btnMultiplicationTable)
        val btnFactorial = findViewById<MaterialButton>(R.id.btnFactorial)

        // Asigna acciones a los botones
        btnDecimalExpression.setOnClickListener {
            val result = decimalExpression(2.5, 3.5, 4.0)
            etMultilineInput.setText("Resultado de la expresión: $result")
        }

        btnAnalyzeValues.setOnClickListener {
            analyzeValues()
        }

        btnMultiplicationTable.setOnClickListener {
            val result = multiplicationTable(5)
            etMultilineInput.setText(result)
        }

        btnFactorial.setOnClickListener {
            val factorialResult = factorial(5)
            etMultilineInput.setText("Factorial de 5 es: $factorialResult")
        }
    }

    // Función para la expresión con decimales
    private fun decimalExpression(a: Double, b: Double, c: Double): Double {
        return (a + b) * c
    }

    // Función para analizar valores
    private fun analyzeValues() {
        val values = mutableListOf<Int>() // Lista para almacenar los valores ingresados
        var currentIndex = 0 // Índice del valor actual que se está solicitando

        // Función para mostrar el cuadro de diálogo y solicitar un valor
        fun showInputDialog() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Ingrese el valor ${currentIndex + 1} de 10")

            // Crear un EditText para que el usuario ingrese el valor
            val input = EditText(this)
            input.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_SIGNED
            builder.setView(input)

            // Configurar el botón "Aceptar"
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                val value = input.text.toString().toIntOrNull() ?: 0 // Convertir el valor a entero
                values.add(value) // Agregar el valor a la lista
                currentIndex++ // Incrementar el índice

                if (currentIndex < 10) {
                    // Si aún faltan valores, mostrar el cuadro de diálogo nuevamente
                    showInputDialog()
                } else {
                    // Si ya se ingresaron los 10 valores, calcular y mostrar los resultados
                    val negatives = values.count { it < 0 }
                    val positives = values.count { it > 0 }
                    val multiplesOf15 = values.count { it % 15 == 0 }
                    val sumEvens = values.filter { it % 2 == 0 }.sum()

                    val result = """
                        Resultados:
                        a) Valores negativos: $negatives
                        b) Valores positivos: $positives
                        c) Múltiplos de 15: $multiplesOf15
                        d) Suma de valores pares: $sumEvens
                    """.trimIndent()

                    // Mostrar los resultados en el TextInputEditText
                    etMultilineInput.setText(result)
                }
                dialog.dismiss() // Cerrar el cuadro de diálogo
            }

            // Configurar el botón "Cancelar"
            builder.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss() // Cerrar el cuadro de diálogo
            }

            // Mostrar el cuadro de diálogo
            builder.show()
        }

        // Iniciar el proceso mostrando el primer cuadro de diálogo
        showInputDialog()
    }

    // Función para la tabla de multiplicar
    private fun multiplicationTable(number: Int): String {
        val table = StringBuilder()
        for (i in 1..10) {
            table.append("$number x $i = ${number * i}\n")
        }
        return table.toString()
    }

    // Función para el factorial
    private fun factorial(number: Int): Long {
        return if (number == 0 || number == 1) 1 else number * factorial(number - 1)
    }
}




