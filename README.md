# Java-file-encryptor-decryptor
## Descripción
El proyecto consta de las funcionalidades de cifrar y decifrar archivos. Para cifrar el archivo se requiere una contraseña dada por el usuario 
a apratir de la cual se genera una clave de 128 bit con el algoritmo PBKDF2 y finalmente se realiza un cifrado con AES en modo CBC.
El proyecto se realiza en el contexto de aprendizaje del curso de seguridad.

## Implementación
* La herramienta se desarrolla en el lenguaje Java y la mayor parte de su logica hace uso de la libreria de seguridad del SDK bajo el 
estándar de arquitectura de criptografía https://docs.oracle.com/javase/7/docs/technotes/guides/security/StandardNames.html .

* La herramienta consta de una capa de la lógica y servicios para el cifrado y descifrado, una interfaz grafica de usuario y una capa de integración y control de los eventos.
* La interfaz de usuario permite: 
  * cargar un archivo en memoria del cual se mostrará su ubicación.
  * cifrar el archivo en memoria
  * descifrar el archivo en memoria
Adicinalmente la interfaz permite mostrar mensajes de control al usuario.

### Cifrado
Se debe cargar un archivo en memoria y luego presionar el boton de cifrar. Acto seguido se pedira al usuario una contraseña para cifrar el archivo e internamente 
la herramienta generará una clave de 128 bit a partir de la contraseña dada con el algoritmo PBKDF2. También se genera un vector de inicialización y un parametro SALT haciendo
uso del secure random provisto por java. Luego se calcula el hash SHA-1 del archivo y se genera un archivo de salida y se procede a escribir en su seccion inicial la siguiente 
información el Hash [20 bytes], el SALT [24 bytes] y el iv [16]. De esta forma se crea una cabecera [60 bytes] en el archivo de encrypción que contiene dicha información para ser
leida antes de decifrar. Consiguiente, se procede a cifrar la información del archivo haciendo uso de cipher y se crea la especificación necesaria para el uso de AES y finalmente 
se realiza la lectura de datos del archivo origen, su cifrado por bloques con cipher y la escritura en el archivo de salida que también contiene la cabecera.
### Descifrado
Se debe cargar el archivo cifrado a memoria y luego presionar el botón descifrar. consiguiente se pedira al usuario la contraseña con la cual se cifró el archivo. Al propocionar la 
contraseña el sistema iniciara la lectura, desde la cabecera del archivo, del SALT necesario para generar la clave mediante PBKDF2 y del vector de inicializaón para decifrar. Una vez se
calcula la clave, se procede a decifrar el archivo. Este proceso se realiza de forma similar al cifrado con las variantes de que el cipher se configura en modo de decifrador y el archivo de entrada
que se encuentra encriptado se inicia a leer y desencriptar luego de saltarse los bits correspondientes en la cabecera. Finalmente, una vez decifrado el archivo, la herramienta obtiene el hash 
SHA1 del archivo orginal, leyendelo de la cabecera del archivo cifrado y lo calcula con el hash calculado del archivo descifrado resultante. Si los hash coinciden se presenta al usuario
que el archivo conserva su integridad, sino, se adbierte que el archivo pudo ser manipulado.

## Dificultades
* La comprensión del estándar de arquitectura de criptografía debido a que la documentación es poco intuitiva y la falta de familiarización con este esquema.
* El manejo y conversión de datos para conservar la integridad en los procesos de cifrado y descifrado.
* Manejar el cifrado iterativo por bloques para evitar el limite de longitud de archivo que se puede cifrar.

## Conclusiones
* El cifrado es muy útil para conservar información de forma segura y secreta. En especial cuando se utilizan metodos que evitan patrones en los archivos cifrados.
* Puede llegar a representar un alto costo computacional para operaciones recurrentes.
* Es necesario actualizar los metodos de cifrado, porque el poder computacional actual permite romper algunos metodos para el aseguramineto de los datos y a medida que la capacidad
computacional aumente los métodos de cifrado deberan aumentar su complejidad.
