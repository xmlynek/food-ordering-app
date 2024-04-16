import React, {useEffect, useState} from "react";
import {useKeycloak} from "@react-keycloak/web";
import ProfileDetails from "../components/Profile/ProfileDetails.tsx";


const ProfilePage: React.FC = ({}) => {

  const [userData, setUserData] = useState<any>();

  const {keycloak} = useKeycloak();

  const handleModifyAccount = async () => {
    await keycloak.accountManagement();
  }

  useEffect(() => {
    keycloak.loadUserInfo().then(() => {
      setUserData(keycloak.userInfo)
    })
  }, []);


  return (
     <ProfileDetails userData={userData} onModifyAccount={handleModifyAccount} />
  );
}

export default ProfilePage;