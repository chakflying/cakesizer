$files = Get-ChildItem -Path "./app/src/main/materials/" -File -Filter "*.mat"

foreach ($file in $files) {
    $filename = [System.IO.Path]::GetFileNameWithoutExtension($file.FullName)
    matc -p mobile -a all -o "./app/src/main/assets/materials/$filename.filamat" $file.FullName
}