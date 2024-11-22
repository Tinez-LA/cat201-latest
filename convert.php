<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST' && !empty($_FILES['fileUpload'])) {
    $uploadDir = 'uploads/';
    foreach ($_FILES['fileUpload']['tmp_name'] as $key => $tmpName) {
        $fileName = basename($_FILES['fileUpload']['name'][$key]);
        $uploadFile = $uploadDir . $fileName;

        if (move_uploaded_file($tmpName, $uploadFile)) {
            $convertedFileName = pathinfo($fileName, PATHINFO_FILENAME) . '.pdf';
            $convertedFilePath = 'converted/' . $convertedFileName;
            $command = "java -jar txttopdfconverter.java $uploadFile $convertedFilePath";
            exec($command, $output, $returnVar);

            if ($returnVar == 0) {
                echo "File converted successfully: <a href='$convertedFilePath'>Download $convertedFileName</a><br>";
            } else {
                echo "Error converting file: $fileName<br>";
            }
        } else {
            echo "Failed to upload file: $fileName<br>";
        }
    }
} else {
    echo "No file uploaded.";
}
?>
