import { StatusBar } from "expo-status-bar";
import { Alert, Button, StyleSheet, Text, TextInput, View } from "react-native";
import * as FileSystem from "expo-file-system";
import { useEffect, useState } from "react";

const FILE_PATH = FileSystem.documentDirectory;

export default function App() {
  const [fileContent, setFileContent] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");
  const [phoneNumberFromFile, setPhoneNumberFromFile] = useState("");
  const [tempText, setTempText] = useState("Hello, world!");

  useEffect(() => {
    console.log("File path: ", FILE_PATH);
  }, []);

  const writeToFile = async () => {
    FileSystem.writeAsStringAsync(
      FileSystem.documentDirectory + "test.txt",
      tempText,
      { encoding: FileSystem.EncodingType.UTF8 }
    );
    console.log("File written!");
  };

  const readDataFromFile = async () => {
    const data = await FileSystem.readAsStringAsync(
      FileSystem.documentDirectory + "test.txt",
      { encoding: FileSystem.EncodingType.UTF8 }
    );
    setFileContent(data);
  };

  const savePhoneNumberToFile = async () => {
    // Validate phone number and save it to file
    if (!phoneNumber) {
      Alert.alert("Please enter a phone number");
      return;
    }
    if (!/^\d{10}$/.test(phoneNumber)) {
      Alert.alert("Please enter a valid phone number");
      return;
    }

    await FileSystem.writeAsStringAsync(
      FILE_PATH + "phonenumber.txt",
      phoneNumber,
      {
        encoding: FileSystem.EncodingType.UTF8,
      }
    );
    Alert.alert("Phone number saved!");
  };

  const readPhoneNumberFromFile = async () => {
    const data = await FileSystem.readAsStringAsync(
      FILE_PATH + "phonenumber.txt",
      {
        encoding: FileSystem.EncodingType.UTF8,
      }
    );
    setPhoneNumberFromFile(data);
  };

  return (
    <View style={styles.container}>
      <Text
        style={{
          fontSize: 25,
          fontWeight: "bold",
          marginBottom: 20,
        }}
      >
        File System
      </Text>

      <View
        style={{
          width: "100%",
          padding: 10,
          margin: 10,
          borderWidth: 1,
          borderColor: "black",
        }}
      >
        <TextInput
          value={phoneNumber}
          onChangeText={setPhoneNumber}
          placeholder="Enter Phone number"
          style={{
            borderWidth: 1,
            borderColor: "black",
            padding: 10,
            width: "100%",
            borderRadius: 5,
          }}
          keyboardType="phone-pad"
        />
        <Button title="Save Phone number" onPress={savePhoneNumberToFile} />

        <Text
          style={{
            fontSize: 16,
            padding: 10,
            backgroundColor: "lightgray",
          }}
        >
          Phone number: {phoneNumberFromFile}
        </Text>
        <Button title="Read Phone number" onPress={readPhoneNumberFromFile} />
      </View>

      <View
        style={{
          margin: 10,
          width: "100%",
          borderWidth: 1,
          borderColor: "black",
          padding: 10,
        }}
      >
        <Text>File System</Text>
        <TextInput
          value={tempText}
          onChangeText={setTempText}
          style={{
            borderWidth: 1,
            borderColor: "black",
            padding: 10,
            width: "100%",
            borderRadius: 5,
          }}
        />
        <Button title="Write to file" onPress={writeToFile} />
        <Button title="Read from file" onPress={readDataFromFile} />
        <Text
          style={{
            fontSize: 16,
            padding: 10,
            backgroundColor: "lightgray",
          }}
        >
          File Content: {fileContent}
        </Text>
      </View>

      <StatusBar style="auto" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: "#fff",
    alignItems: "center",
    justifyContent: "center",
    paddingHorizontal: 20,
  },
});
