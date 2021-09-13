/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2021 DBeaver Corp and others
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jkiss.dbeaver.ext.postgresql.model.data;

import org.jkiss.dbeaver.ext.postgresql.model.PostgreDataType;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCPreparedStatement;
import org.jkiss.dbeaver.model.exec.jdbc.JDBCSession;
import org.jkiss.dbeaver.model.impl.jdbc.data.handlers.JDBCStringValueHandler;
import org.jkiss.dbeaver.model.struct.DBSTypedObject;
import org.jkiss.utils.ArrayUtils;

import java.sql.SQLException;
import java.sql.Types;

/**
 * PostgreStringValueHandler
 */
public class PostgreStringValueHandler extends JDBCStringValueHandler {

    public static final PostgreStringValueHandler INSTANCE = new PostgreStringValueHandler();

    @Override
    public void bindParameter(JDBCSession session, JDBCPreparedStatement statement, DBSTypedObject paramType, int paramIndex, Object value) throws SQLException {
        if (paramType.getTypeID() == Types.OTHER || ArrayUtils.contains(PostgreDataType.getOidTypes(), paramType.getTypeName())) {
            if (value == null) {
                statement.setNull(paramIndex, paramType.getTypeID());
            } else {
                statement.setObject(paramIndex, value.toString(), Types.OTHER);
            }
        } else {
            super.bindParameter(session, statement, paramType, paramIndex, value);
        }
    }
}
