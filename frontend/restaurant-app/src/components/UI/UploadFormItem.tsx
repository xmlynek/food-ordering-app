import {Form, FormInstance} from "antd";
import UploadComponent from "./UploadComponent.tsx";
import React from "react";

interface UploadFormItemProps {
  form: FormInstance;

}

const UploadFormItem: React.FC<UploadFormItemProps> = ({form}: UploadFormItemProps) => {


  return (
      <Form.Item
          name="image"
          label="Image"
          valuePropName="fileList"
          getValueFromEvent={e => {
            if (Array.isArray(e)) {
              return e;
            }
            return e?.fileList;
          }}
          rules={[{required: true, message: 'Please upload an image!'}]}
      >
        <UploadComponent onFileChange={(img) => form.setFieldValue("image", img)}/>
      </Form.Item>
  );
}

export default UploadFormItem;