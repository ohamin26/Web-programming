import { Link, useNavigate } from 'react-router-dom';
import '../css/school.css';
import { JwtPayload, jwtDecode } from 'jwt-decode';
import { useToken } from '../context/TokenContext';
import { useEffect, useState } from 'react';

interface SchoolInfo {
    id: string;
    name: string;
}
export const School = () => {
    const [schoolInfo, setSchoolInfo] = useState<SchoolInfo[]>([]);
    const [loading, setLoading] = useState(true);
    const { token } = useToken();
    const navigate = useNavigate();
    let id: string | undefined;
    if (token) {
        const decodedToken: JwtPayload & { id: string } = jwtDecode(token);
        id = decodedToken.id;
    }

    const getSchoolInfo = async () => {
        try {
            const response = await (
                await fetch(`http://localhost:8080/api/school/all`)
            ).json();

            setSchoolInfo(response);
            setLoading(false);
        } catch (error: any) {
            console.log('학교정보 조회 실패');
        }
    };
    const onClick = (item: string) => {
        if (item !== '한국공학대학교') {
            alert('지원하지 않는 기능입니다.');
        } else {
            navigate(`/home`);
        }
    };
    useEffect(() => {
        getSchoolInfo();
    }, []);

    console.log(schoolInfo);

    return (
        <div className="cover_school">
            {loading
                ? 'loading'
                : schoolInfo.map((item) => (
                      <button className="btn-school learn-more">
                          <div onClick={() => onClick(item.name)}>
                              {item.name}
                          </div>
                      </button>
                  ))}
        </div>
    );
};
