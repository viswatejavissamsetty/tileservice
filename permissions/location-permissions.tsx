import * as Location from "expo-location";
import { useEffect, useState } from "react";
import { Alert, Button, Text, View } from "react-native";

export function LocationPermission() {
  const [currentLocation, setCurrentLocation] =
    useState<Location.LocationObject | null>(null);
  const [locationPermission, setLocationPermission] = useState(false);

  useEffect(() => {
    (async () => {
      const { status: forgroundStatus } =
        await Location.requestForegroundPermissionsAsync();
      if (forgroundStatus !== "granted") {
        Alert.alert("Permission to access location was denied");
      }
      const { status: backgroundStatus } =
        await Location.requestBackgroundPermissionsAsync();
      if (backgroundStatus !== "granted") {
        Alert.alert("Permission to access location was denied");
      }
      setLocationPermission(true);
    })();
  }, []);

  const getCurrrentLocation = () => {
    console.log("Getting current location");
    Location.getLastKnownPositionAsync()
      .then((location) => {
        setCurrentLocation(location);
        console.log(location);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <View>
      <Text
        style={{
          fontSize: 20,
          textAlign: "center",
          marginTop: 20,
        }}
      >
        {locationPermission
          ? "Location permission granted"
          : "Permission not granted"}
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
