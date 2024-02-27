import React from "react";
import {Form, FormInstance, Input, InputNumber, Upload} from "antd";

interface CreateMenuFormProps {
  form: FormInstance;
}

const CreateMenuForm: React.FC<CreateMenuFormProps> = ({form}: CreateMenuFormProps) => {
  return (
      <Form form={form} layout="vertical" name="addMenuForm">
        <Form.Item
            name="name"
            label="Name"
            rules={[{required: true, message: 'Please input the menu name!'}]}
        >
          <Input/>
        </Form.Item>
        <Form.Item
            name="description"
            label="Description"
            rules={[{required: true, message: 'Please input the menu description!'}]}
        >
          <Input.TextArea rows={4}/>
        </Form.Item>
        <Form.Item
            name="price"
            label="Price"
            rules={[{required: true, message: 'Please input the menu price!'}]}
        >
          <InputNumber min={0}
                       formatter={value => `€ ${value}`.replace(/\B(?=(\d{3})+(?!\d))/g, ',')}/>
        </Form.Item>
        <Form.Item
            name="upload"
            label="Upload Image"
            valuePropName="fileList"
            getValueFromEvent={e => {
              if (Array.isArray(e)) {
                return e;
              }
              return e?.fileList;
            }}
            extra="Optional field for uploading an image."
        >
          <Upload
              name="logo"
              listType="picture-card"
              className="avatar-uploader"
              showUploadList={false}
              action="/upload" // Your backend endpoint
              // beforeUpload={beforeUpload}
              // onChange={handleImageChange} // Add this prop to handle change
          >
            {/*{imageUrl ? <img src={imageUrl} alt="avatar" style={{width: '100%'}}/> :*/}
            {/*    <div style={{marginTop: 8}}>Upload</div>}*/}
          </Upload>
        </Form.Item>
      </Form>
  );
}

export default CreateMenuForm;