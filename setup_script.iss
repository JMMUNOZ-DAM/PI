; Script de Inno Setup para NETFIX
; Requiere Inno Setup 6 o superior

#define MyAppName "NETFIX"
#define MyAppVersion "1.0"
#define MyAppPublisher "Juanma Muñoz"
#define MyAppURL "https://github.com/JMMUNOZ-DAM/PI"
#define MyAppExeName "netfix.jar"

[Setup]
; Identificador único de la aplicación (Generado aleatoriamente, no cambiar en futuras actualizaciones)
AppId={{A3D7C921-8274-4B39-9385-28827364510D}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={autopf}\{#MyAppName}
DisableProgramGroupPage=yes
; Icono del instalador (Debe ser un archivo .ico válido)
SetupIconFile=src\main\resources\img\netfix_N.ico
UninstallDisplayIcon={app}\netfix_N.ico
; Imagen lateral grande del instalador (Banner vertical)
WizardImageFile=src\main\resources\img\netfix_installer_banner.png
; Icono pequeño en la esquina superior derecha
WizardSmallImageFile=src\main\resources\img\netfix_N.png
OutputBaseFilename=NETFIX_Installer
Compression=lzma
SolidCompression=yes
WizardStyle=modern

[Languages]
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
; IMPORTANTE: Asegúrate de haber hecho "Clean and Build" en NetBeans antes de compilar este script
; El * incluye todas las dependencias si has usado el maven-shade-plugin correctamente
Source: "target\netfix-1.0-SNAPSHOT.jar"; DestDir: "{app}"; DestName: "netfix.jar"; Flags: ignoreversion
; Copiamos el icono para usarlo en los accesos directos
Source: "src\main\resources\img\netfix_N.ico"; DestDir: "{app}"; Flags: ignoreversion

[Icons]
; Crea el acceso directo en el Menú Inicio
Name: "{autoprograms}\{#MyAppName}"; Filename: "{cmd}"; Parameters: "/c start javaw -jar ""{app}\netfix.jar"""; IconFilename: "{app}\netfix_N.ico"; Comment: "Iniciar NETFIX"

; Crea el acceso directo en el Escritorio (si el usuario lo marca)
Name: "{commondesktop}\{#MyAppName}"; Filename: "{cmd}"; Parameters: "/c start javaw -jar ""{app}\netfix.jar"""; IconFilename: "{app}\netfix_N.ico"; Tasks: desktopicon

[Run]
; Ejecutar la aplicación tras finalizar la instalación
Filename: "{cmd}"; Parameters: "/c start javaw -jar ""{app}\netfix.jar"""; Description: "{cm:LaunchProgram,{#MyAppName}}"; Flags: nowait postinstall skipifsilent

[Code]
// Función para verificar si Java está instalado (Opcional básico)
function InitializeSetup(): Boolean;
var
  ErrorCode: Integer;
begin
  if not ShellExec('open', 'java', '-version', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode) then
  begin
    if MsgBox('Parece que Java no está instalado o no se encuentra en el sistema.' + #13#10 +
       'Esta aplicación requiere Java para funcionar.' + #13#10 + 
       '¿Deseas continuar de todas formas?', mbConfirmation, MB_YESNO) = IDNO then
    begin
      Result := False;
      Exit;
    end;
  end;
  Result := True;
end;
