import React, {useState} from "react";
import {GetProp, message, Upload, UploadProps} from "antd";
import {LoadingOutlined, UploadOutlined} from "@ant-design/icons";

interface UploadComponentProps {
  onFileChange: (file: FileType | undefined) => void;
}

export type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];


const UploadComponent: React.FC<UploadComponentProps> = ({onFileChange}) => {
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();


  const getBase64 = (img: FileType, callback: (url: string) => void) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result as string));
    reader.readAsDataURL(img);
  };

  const beforeUpload = (file: FileType) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
      message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
  };

  const handleChange: UploadProps['onChange'] = (info) => {
    console.log(info)
    if (info.file.status === 'uploading') {
      setLoading(true);
      return;
    }

    if (info.file.status === 'done') {
      onFileChange(info.file.originFileObj);

      // Get this url from response in real world.
      getBase64(info.file.originFileObj as FileType, (url) => {
        setLoading(false);
        setImageUrl(url);
      });
    } else if (info.file.status === 'removed') {
      onFileChange(undefined);
    }
  };

  const uploadButton = (
      <>
        {loading ? <LoadingOutlined/> :
            <div><UploadOutlined/>
              <div style={{marginTop: 8}}>Upload</div>
            </div>}
      </>
  );


  return (
      <Upload
          name="logo"
          listType="picture-card"
          className="avatar-uploader"
          showUploadList={false}
          // TODO: Replace with real API endpoint
          action="https://run.mocky.io/v3/435e224c-44fb-4773-9faf-380c5e6a2188"
          beforeUpload={beforeUpload}
          onChange={handleChange}
      >
        {imageUrl ? <img src={imageUrl} alt="avatar" style={{width: '100%'}}/> : uploadButton}
      </Upload>
  );
}

export default UploadComponent;