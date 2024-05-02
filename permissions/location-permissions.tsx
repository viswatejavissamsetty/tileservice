import * as Location from "expo-location";
import { useEffect, useState } from "react";
import { Alert, Button, Text, View } from "react-native";

export function LocationPermission() {
  const [currentLocation, setCurrentLocation] =
    useState<Location.LocationObject | null>(null);

  useEffect(() => {
    (async () => {
      const { status } = await Location.requestBackgroundPermissionsAsync();
      if (status !== "granted") {
        Alert.alert("Permission to access location was denied");
      }
    })();
  }, []);

  const getCurrrentLocation = async () => {
    const location = await Location.getCurrentPositionAsync({});
    setCurrentLocation(location);
  };

  return (
    <View
      style={{
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
      }}
    >
      <Text
        style={{
          fontSize: 20,
        }}
      >
        Location permission granted!
      </Text>
      <Button title="Get current location" onPress={getCurrrentLocation} />
      {currentLocation && (
        <Text
          style={{
            fontSize: 16,
          }}
        >
          Latitude: {currentLocation.coords.latitude}, Longitude:{" "}
          {currentLocation.coords.longitude}
        </Text>
      )}
    </View>
  );
}
