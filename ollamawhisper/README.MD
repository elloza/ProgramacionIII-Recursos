# Ejemplo de uso de Maven para cargar bibliotecas a partir de Jar remotos

Se puede observer en el pom.xml de este proyecto como cargar bibliotecas externas a partir de una URL a un JAR.

En el fichero pom.xml se describe el procedimiento y el plugin empleado.


# Ejemplo proyecto combinación de Whisper y Ollama

En este ejemplo se va a utilizar la combinación de Whisper y Ollama para obtener una respuesta formateada a partir de lenguaje natural.

El proceso es el siguiente:

1. Se obtiene un audio y se trata de trascribir a texto (Speech to Text - STT). Para esto se utiliza la librería de OpenAI Whisper. Sin embargo se utilizará un wrapper de Java denominado [WhisperJNI](https://github.com/GiviMAD/whisper-jni).

2. Tras obetener el texto, se utilizará un LLM para obtener una respuesta formateada a partir de lenguaje natural. Se deberá diseñar un prompt para el LLM que describa la tarea a realizar y el texto obtenido en el paso 1. 

Por ejemplo:

```
Lenguaje natural:

"Tengo que entregar la práctica de programación III el día 12 de Diciembre a las 16:30 de 2024, que no se me olvide realizar el informe y el código."

Texto estructurado como JSON como una tarea que tiene titulo, descripción, fecha y hora:

JSON objetivo
{
    "title": "Entregar práctica de programación III",
    "description": "Tengo que entregar la práctica de programación III el día 12 de Diciembre a las 16:30 de 2024, que no se me olvide realizar el informe y el código.",
    "date": "2024-12-12",
    "time": "16:30"
}

```


**Prompt:**
```
Eres un asistente experto en extraer información estructurada.
Debes extraer la información en formato JSON con los siguientes campos:
{
    "titulo": "título corto de la tarea",
    "date": "fecha mencionada como dd-mm-yyyy",
    "time": "hora si la hubiera, en caso contrario vacíO"
    "contenido": "descripción detallada de la tarea en formato MarkDown"
}
Responde ÚNICAMENTE con el JSON, sin explicaciones adicionales y sin comentarios.
```

Para la comunicación con LLMs desde Java se utiliza [Ollama4j Java](https://ollama4j.github.io/ollama4j/intro). Esto permite la conexión con múltiples modelos open source ofrecidos por ollama. En este ejemplo se utilizará un modelo de META llamado [LLama3.2-3B](https://ollama.com/library/llama3.2).

3. Se deberá asegurar la estructura del JSON obtenido en el paso 2. Por ejemplo:

```
{
    "title": "Entregar práctica de programación III",
    "description": "Tengo que entregar la práctica de programación III el día 12 de Diciembre a las 16:30 de 2024, que no se me olvide realizar el informe y el código.",
    "date": "2024-12-12",    
    "time": "16:30"
}
```

Finalmente, tras construir el objeto tarea a partir del JSON, se podrá agregar esta tarea a un calendario o agenda.


# Requisitos:

## Whisper

Es necesario proporcionar un modelo. Podéis descargar los modelos de esta ubicación:

https://huggingface.co/ggerganov/whisper.cpp/tree/main

Recomendado: ggml-large-v3-turbo-q5_0.bin

**Es necesario proporcionar el path del modelo al programa**


## Ollama

Es necesario instalar Ollama

https://ollama.com/

Y posteriormente bajar un modelo, por ejemplo Llama3.2

## Instalación de un modelo con Ollama

Para instalar un modelo con Ollama, sigue los siguientes pasos:

1. Asegúrate de tener Ollama instalado en tu sistema. Puedes descargarlo desde [aquí](https://ollama.com/).

2. Abre una terminal y ejecuta el siguiente comando para descargar el modelo deseado. En este ejemplo, descargaremos el modelo `Llama3.2`:

ollama pull llama3.2




