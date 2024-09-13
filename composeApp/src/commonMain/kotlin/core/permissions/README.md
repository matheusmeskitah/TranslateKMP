# Módulo core para lidar com permissões

O módulo Permissions provê um conjunto de abstrações e utilitários para lidar com a requisição e checagem de permissão em dispositivos Android e iOS.

#### Features

- Requisitar uma permissão
- Checar se o app já possui um determinada permissão
- Redirecionar usuário para a tela de configurações

#### Índice

- [Módulo core para lidar com permissões](#módulo-core-para-lidar-com-permissões-)
- [Implementação no Android](#implementação-no-android-)
- [Implementação no iOS](#implementação-no-ios-)
- [Status de permissão](#status-de-permissão)
- [Tratamento de Erros](#tratamento-de-erros-)
- [Contribuindo](#contribuindo-)

#### Implementação no Android 🤖

Incluir a dependência do `multiplatform.permissions.permissionsCore` no common

#### Inclusão do módulo nas features KMP

```kotlin
// build.gradle.kts
kotlinMultiplatform {
    common {
        main {
            implementation(platformLibs.multiplatform.permissions.permissionsCore)
        }
    }
}
```

#### Inicialização do módulo de Permissions Core

Crie uma instância da Permissions usando o método `rememberPermissions` que deverá retornar uma instância do Permissions.

```kotlin
    ....
    @Composable
    private fun Foo() {
        val permissionCore = rememberPermissions()
        ....
    }
```

#### Validar estado de uma permissão ✅

Usando o método `checkPermission` é possível obter o retorno do enum `PermissionStatus` que inidica o estado da permissão indicada.

```kotlin
val permissionsStatus = permissionCore.checkPermissions()

when(permissionsStatus) {
    PermissionStatus.GRANTED -> TODO()
    PermissionStatus.PENDING -> TODO()
    PermissionStatus.DENIED -> TODO()
}
```

Realize o tratamento de acordo com o estado retornado.

#### Requisitar uma nova permissão

Utilize o método `requestPermission` para requisitar uma nova permissão ao usuário. Este método recebe o parâmetro _**permissionName**_ do tipo `PermissionName` e o _*onResult*_ que trata-se de um callback com o status da requisição do tipo `PermissionStatus`

```kotlin
@Composable
private fun Foo() {
    val hasCameraPermission by remember { mutableStateOf(false) }
    val permissionCore = rememberPermission()

    val requestCameraPermission = remember(permissionCore) {
        {
            permissionCore.requestPermission(permissionName = PermissionName.CAMERA) {
                hasCameraPermission = it == PermissionStatus.GRANTED
                if(it == PermissionStatus.DENIED) {
                    permissionCore.openSettings { result ->
                        logger.d("Open settings result: $result")
                    }
                }
            }
        }
    }

    Button(onClick = requestCameraPermission)
}
```

#### Abrir configurações

O módulo Permission Core provê um método que possibilita redirecionar o usuário para a tela de configuração do dispositivo. Esse método deverá ser usado nos cenários em que o usuário negou a primeira solicitação de permissão e precisará ir diretamente nas configurações de aplicativos no dispositivo para que possa conceder a permissão manualmente.

```kotlin
    permissionCore.openSettings { result ->
        logger.d("Open settings result: $result")
    }
```

---

## Implementação no iOS 🍎

Siga os passos abaixo para utilzação do Permissions Core em uma projeto iOS

#### Inicialização o módulo Permissions Core

Crie uma instância da Permissions usando `PermissionsHelper.shared` que provê uma instância do módulo e permite acesso aos seus métodos

#### Validar estado da permissão ✅

Usando o método `checkPermission` é possível obter o retorno do enum `PermissionStatus` que inidica o estado da permissão.

```swift
let result: PermissionStatus = PermissionsHelper.shared.checkPermission(permission: PermissionName.camera)

if (result == PermissionStatus.granted) {
    print("Permissão concedida")
}

if (result == PermissionStatus.pending) {
    print("Permissão pendente")
}

if (result == PermissionStatus.denied) {
    print("Permissão negada")
}
```

Realize o tratamento de acordo com o estado retornado.

#### Requisitar uma nova permissão

Utilize o método `requestPermission` para requisitar uma nova permissão ao usuário. Este método recebe o parâmetro _**permissionName**_ do tipo `PermissionName` e o _*onResult*_ que trata-se de um callback com o status da requisição do tipo `PermissionStatus`

```swift

PermissionsHelper.shared.requestPermission(permission: PermissionName.locationAlways) { permissionStatus in
    if (permissionStatus == PermissionStatus.granted) {
        print("Permissão concedida")
    } else {
        print("Permissão negada")
    }
```

#### Abrir configurações

O módulo Permission Core provê um método que possibilita redirecionar o usuário para a tela de configuração do dispositivo. Esse método deverá ser usado nos cenários em que o usuário negou a primeira solicitação de permissão e precisará ir diretamente nas configurações de aplicativos no dispositivo para que possa conceder a permissão manualmente.

```swift
    PermissionsHelper.shared.openSettings { result in
        logger.d("Open permission result: \(result)")
    }
```

---

### Status de permissão

O status de uma permissão, seja a checagem ou requisição, é inidicada através do Enum PermissionStatus que indica os status da seguinte forma:

```kotlin
public enum class PermissionStatus {
    /**
     * A permissão foi concedida.
     */
    GRANTED,
    /**
     * A permissão está pendente, deverá ser requisitada.
     */
    PENDING,
    /**
     * A permissão foi permanentemente negada. Deverá ser concedida via configurações
     */
    DENIED
}
```

### Links úteis

- Samples: Caso queira verificar as funcionalidades, basta executar um dos samples de [iOS](https://github.com/stone-payments/mobile-platform/tree/main/multiplatform/permissions/sample/ios/PermissionsSample) ou [Android](https://github.com/stone-payments/mobile-platform/tree/main/multiplatform/permissions/sample/android).

### Contribuindo 👩‍💻

[Contribuições](https://github.com/stone-payments/mobile-platform/wiki/Como-Contribuir) são bem vindas! Se você encontrar algum erro ou tiver alguma sugestão de melhorias, por favor sinta-se livre para submeter um _pull request_ em [GitHub](https://github.com/stone-payments/mobile-platform).
