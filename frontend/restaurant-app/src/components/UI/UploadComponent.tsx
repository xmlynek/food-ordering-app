import React, {useCallback, useState} from "react";
import {Button, GetProp, message, Space, Upload, UploadFile, UploadProps} from "antd";
import {DeleteOutlined, LoadingOutlined, UploadOutlined} from "@ant-design/icons";
import {UploadChangeParam} from "antd/es/upload";

interface UploadComponentProps {
  onFileChange: (file: UploadFile | undefined) => void;
}

export type FileType = Parameters<GetProp<UploadProps, 'beforeUpload'>>[0];


const UploadComponent: React.FC<UploadComponentProps> = ({onFileChange}) => {
  const [loading, setLoading] = useState(false);
  const [imageUrl, setImageUrl] = useState<string>();


  const getBase64 = (img: UploadFile, callback: (url: string) => void) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result as string));
    // @ts-ignore
    reader.readAsDataURL(img);
  };

  const beforeUpload = (file: FileType) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
      message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 <= 4;
    if (!isLt2M) {
      message.error('Image must be smaller than 4MB!');
    }
    return false;
  };

  const handleChange: UploadProps['onChange'] = useCallback((info: UploadChangeParam) => {
    if (info.file.status === 'uploading') {
      setLoading(true);
      setImageUrl('');
      // info.file.status = 'done';
    }

    if (info.file.status !== 'done') {
      onFileChange(info.file);
      getBase64(info.file, (imageUrl) => {
        const img = new Image();
        img.src = imageUrl;
        img.addEventListener('load', function () {
          setLoading(false);
          setImageUrl(imageUrl);
        });
      });
    }
    if (info.file.status === 'removed') {
      onFileChange(undefined);
      setImageUrl('');
    }
  }, []);

  const handleRemove = () => {
    setImageUrl('');
    setLoading(false);
    onFileChange(undefined);
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
      <Space>
        <Upload
            name="image"
            listType="picture-card"
            className="avatar-uploader"
            showUploadList={false}
            beforeUpload={beforeUpload}
            onChange={handleChange}
        >
          {imageUrl ? <img src={imageUrl} alt="avatar"
                           style={{
                             height: '100px',
                             width: '100px',
                             objectFit: 'cover'
                           }}/> : uploadButton}
        </Upload>
        {imageUrl && <Button onClick={handleRemove} icon={<DeleteOutlined />}>Clear selection</Button>}
      </Space>
  );
}

export default UploadComponent;