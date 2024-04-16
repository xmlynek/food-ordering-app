import React from "react";
import {Avatar, Button, Card, Col, Descriptions, Row, Spin, Typography} from "antd";
import {UserOutlined} from "@ant-design/icons";

import styles from './ProfileDetails.module.css';

interface ProfileDetailsProps {
  userData: any;
  onModifyAccount: () => void;
}

const ProfileDetails: React.FC<ProfileDetailsProps> = ({
                                                         userData,
                                                         onModifyAccount
                                                       }: ProfileDetailsProps) => {

  if (!userData) {
    return (
        <div className={styles.centerContent}>
          <Spin size="large"/>
        </div>
    );
  }

  return (
      <Row justify="center" className={styles.cardContainer}>
        <Col xs={24} sm={12} md={8} lg={6}>
          <Card
              className={styles.card}
              bordered={false}
          >
            <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
              <Avatar size={100} icon={<UserOutlined/>} className={styles.avatar}/>
              <Typography.Title level={4}
                                className={styles.title}>{userData.name}</Typography.Title>
              <Descriptions column={1} size="small">
                <Descriptions.Item
                    label="Username">{userData.preferred_username}</Descriptions.Item>
                <Descriptions.Item label="Email">{userData.email}</Descriptions.Item>
              </Descriptions>
              <Button type="primary" onClick={onModifyAccount} block
                      className={styles.changeDetailsButton}>
                Change account details
              </Button>
            </div>
          </Card>
        </Col>
      </Row>
  );
}

export default ProfileDetails;