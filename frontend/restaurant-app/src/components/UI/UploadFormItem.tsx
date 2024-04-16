import {Form, FormInstance} from "antd";
import UploadComponent from "./UploadComponent.tsx";
import React from "react";

interface UploadFormItemProps {
  form: FormInstance;
  required?: boolean;
}

const UploadFormItem: React.FC<UploadFormItemProps> = ({
                                                         form,
                                                         required = true
                                                       }: UploadFormItemProps) => {

  return (
      <Form.Item
          name="image"
          label="Image"
          tooltip={"Images with a resolution of 1920x1080 or more are recommended."}
          valuePropName="fileList"
          getValueFromEvent={e => {
            if (Array.isArray(e)) {
              return e;
            }
            return e?.fileList;
          }}
          rules={[{required: required, message: 'Please upload an image!'},
            () => ({
              validator(_, file) {
                if (file) {
                  const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
                  if (!isJpgOrPng) {
                    return Promise.reject(new Error('You can only upload JPG/PNG file!'));
                  }

                  const isGt2M = file.size / 1024 / 1024 <= 4;
                  if (!isGt2M) {
                    return Promise.reject(new Error('Image must be smaller than 4MB!'));
                  }
                }
                return Promise.resolve();
              }
            })
          ]}
      >
        <UploadComponent onFileChange={(img) => {
          form.setFieldValue("image", img)
          form.validateFields(['image']);
        }
        }/>
      </Form.Item>
  );
}

export default UploadFormItem;